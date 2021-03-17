package com.example.milugarfavorito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    Button boton3;
    EditText lugarET;
    EditText tituloET;

    public static final String EXTRA_TITULO = "com.example.milugarfavorito.titulo";
    public static final String EXTRA_LUGAR = "com.example.milugarfavorito.lugar";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        boton3 = findViewById(R.id.boton3);
        boton3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //Recuperamos los datos de la actividad anterior para enviarla a la siguiente actividad
        Intent intent = getIntent();
        Long calendario = intent.getLongExtra(MainActivity.EXTRA_CALENDARIO, 1);

        //Creamos otro intent mediante el cual pasaremos los datos guardados en esta página para
        //poder pasarlos a la siguiente.
        Intent intent2 = new Intent(this, MapsActivity.class);
        tituloET = findViewById(R.id.tituloET);
        String titulo = tituloET.getText().toString();
        lugarET = findViewById(R.id.lugarET);
        String lugar = lugarET.getText().toString();

        intent2.putExtra(MainActivity.EXTRA_CALENDARIO, calendario);
        intent2.putExtra(Main2Activity.EXTRA_TITULO, titulo);
        intent2.putExtra(Main2Activity.EXTRA_LUGAR, lugar);

        startActivity(intent2);
    }
}
