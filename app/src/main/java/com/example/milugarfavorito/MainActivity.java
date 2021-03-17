package com.example.milugarfavorito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TimePicker.OnTimeChangedListener, DatePicker.OnDateChangedListener {
    TimePicker horaTP;
    DatePicker fechaDP;
    Button boton2;
    Calendar calendar;

    public static final String EXTRA_CALENDARIO = "com.example.milugarfavorito.calendario";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        horaTP = findViewById(R.id.horaTP);
        fechaDP = findViewById(R.id.fechaDP);
        boton2 = findViewById(R.id.boton2);

        //Con esto nos avisará de que el usuario ha metido una nueva hora o un nuevo minuto
        //o ha cambiado el tiempo elegido.
        horaTP.setOnTimeChangedListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fechaDP.setOnDateChangedListener(this);
        }
        boton2.setOnClickListener(this);
        calendar = new GregorianCalendar();
    }

    @Override
    public void onTimeChanged(TimePicker view, int hora, int minuto) {
        //Guardaremos la hora y los minutos
        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, minuto);
    }

    @Override
    public void onDateChanged(DatePicker view, int anio, int mes, int dia) {
        //Guardamos el año, mes y día
        calendar.set(anio, mes,dia);
    }
    @Override
    public void onClick(View v) {
        //Mediante el intent pasamos a la siguiente actividad y pasamos los datos de una actividad a otra
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra(EXTRA_CALENDARIO, calendar.getTimeInMillis());
        startActivity(intent);
    }
}
