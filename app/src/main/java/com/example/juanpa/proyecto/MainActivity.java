package com.example.juanpa.proyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void activityCrearHorario(View view){

        Intent intent = new Intent(this,IngresoCarreraSemestre.class);

        startActivity(intent);

    }

    public void activityConsultarHorario(View view) {

        Intent intent = new Intent(this,ProspectoHorario.class);

        startActivity(intent);
    }

    public void activityGenerarHorario(View view){
        Intent intent = new Intent(this, GenerarHorarioActivity.class);
        startActivity(intent);
    }
}
