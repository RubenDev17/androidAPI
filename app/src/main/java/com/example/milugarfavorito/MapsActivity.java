    package com.example.milugarfavorito;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

    public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mapa;
        Button boton1;
        Long calendario;
        String lugar;
        String titulo;
        TextView tituloeventoTV;
        TextView fechaeventoTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        boton1 = findViewById(R.id.boton1);
        boton1.setOnClickListener(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        //Recuperamos los datos del Main2Activity
        Intent intent = getIntent();
        calendario = intent.getLongExtra(MainActivity.EXTRA_CALENDARIO,1);
        lugar = intent.getStringExtra(Main2Activity.EXTRA_LUGAR);
        titulo = intent.getStringExtra(Main2Activity.EXTRA_TITULO);

        //Mostramos los datosr recuperados del main2activity
        tituloeventoTV = findViewById(R.id.tituloeventoTV);
        tituloeventoTV.setText(titulo);
        fechaeventoTV = findViewById(R.id.fechaeventoTV);
        fechaeventoTV.setText(getDate(calendario, "dd/MM/yyyy hh:mm:ss"));


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        //Vamos hacer que la cámara se mueva automaticamente de donde esta a un sitio que queramos
        //Para eso creamos un objeto de tipo LatLng que nos definirá un lugar en el mapa
        LatLng lugar = new LatLng(41,4); //El contructor recibirá latitud y longitud de donde quiero que se me coleque este mapa
        //Ahora moveremos la cámara del mapa para colocarla en ese lugar
        mapa.moveCamera(CameraUpdateFactory.newLatLng(lugar));
        //Localizar el dispositivo en el mapa
        activarLocalizacion();

        geocode();
    }

    public void activarLocalizacion() {
        //Pediremos permiso para utilizar la localización

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Llamamos a requestPermission haciendo una petición al usuario del permiso para poder utilizarlo
            String[] permisos = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this,permisos, 123);
            return;
        }

        mapa.setMyLocationEnabled(true);
    }
    //Despues veremos el resultado del usuario con onRequestPermissionResult para saber si el usuario me dio o no este permiso
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 123 && permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            activarLocalizacion();
        }
    }

    public void geocode(){
        //Utilizaremos la clase Geocoder que nos permitirá hacer Geocoding
        Geocoder geocoder = new Geocoder(this);
        try {
            //Creamos  la lista de address que llamaremos direcciones y ahi guardaremos el resultado de esta busqueda
            List<Address> direcciones =geocoder.getFromLocationName(lugar, 1);
            //Si el tamañao de la lista es distinto que 0, la primera direccion será mi resultado
            if(direcciones.size() !=0) {
                Address direccion = direcciones.get(0); //Aqui se guardará
                //Le ponemos marcador
                LatLng sitio = new LatLng(direccion.getLatitude(),direccion.getLongitude());
                mapa.addMarker(new MarkerOptions().position(sitio).title(direccion.getLocality()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Con este método pasamos a la siguiente actividad
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //Con este método transformaremos los milisegundos del setTimeInMillis en la fecha que mostramos en la actividad
    public String getDate(long milliSeconds, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
