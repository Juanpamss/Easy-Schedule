package com.example.juanpa.proyecto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class IngresoCarreraSemestre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_carrera_semestre);


        ArrayList<String> array = new ArrayList<String>();

        array.add("I.T");
        array.add("C.S");
        array.add("I.S");

        Spinner spinner = (Spinner) findViewById(R.id.spinnerCarrera);

        ArrayAdapter adaptador = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,array);

        spinner.setAdapter(adaptador);

    }
}
