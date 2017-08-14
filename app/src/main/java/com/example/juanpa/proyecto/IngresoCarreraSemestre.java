package com.example.juanpa.proyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IngresoCarreraSemestre extends AppCompatActivity {

    Spinner spinner, spinnerSemestre, spinnerJornada;
    ArrayList<String> semestre = new ArrayList<String>();
    ArrayList<String> jornada = new ArrayList<String>();
    String grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_carrera_semestre);

        spinner = (Spinner) findViewById(R.id.spinnerCarrera);
        spinnerSemestre = (Spinner) findViewById(R.id.spinnerSemestre);
        spinnerJornada = (Spinner) findViewById(R.id.spinnerJornada);
        TextView textSemestre = (TextView) findViewById(R.id.txtViewSemestre);
        TextView textJornada = (TextView) findViewById(R.id.txtViewJornada);
        Button btnContinuar = (Button) findViewById(R.id.btnContinuar);

        /*spinnerSemestre.setVisibility(View.GONE);
        spinnerJornada.setVisibility(View.GONE);
        textSemestre.setVisibility(View.GONE);
        textJornada.setVisibility(View.GONE);
        btnContinuar.setVisibility(View.GONE);*/

        ArrayList<String> array = new ArrayList<String>();

        array.add("I.T");
        array.add("C.S");
        array.add("I.S");


        ArrayAdapter adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adaptador);

        llenarSemestres();
        llenarHorarios();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                spinnerSemestre.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    public void actividadProspecto(View view) {

        if (spinnerJornada.getSelectedItem().toString().equals("Matutina")) {
            grupo = "GR1";
        } else {
            grupo = "GR2";
        }
        Log.d("grupo",grupo);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url2 = "http://10.0.2.2/horarios.php";
        StringRequest jsRequest = new StringRequest
                (Request.Method.POST, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("response", response);
                            Intent intent = new Intent(IngresoCarreraSemestre.this, ProspectoHorario.class);
                            intent.putExtra("response", response);
                            startActivity(intent);
                        } catch (Throwable t) {
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(IngresoCarreraSemestre.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("semestre", String.valueOf(spinnerSemestre.getSelectedItemPosition() + 1));
                params.put("jornada", grupo);
                return params;
            }
        };
        queue.add(jsRequest);

    }


    public void llenarSemestres() {

        semestre.add("Primero");
        semestre.add("Segundo");
        semestre.add("Tercero");
        semestre.add("Cuarto");
        semestre.add("Quinto");
        semestre.add("Sexto");
        semestre.add("Septimo");
        semestre.add("Octavo");

        spinnerSemestre = (Spinner) findViewById(R.id.spinnerSemestre);

        ArrayAdapter adaptadorSem = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, semestre);

        spinnerSemestre.setAdapter(adaptadorSem);


    }

    public void llenarHorarios() {

        jornada.add("Matutina");
        jornada.add("Vespertina");


        spinnerJornada = (Spinner) findViewById(R.id.spinnerJornada);

        ArrayAdapter adaptadorHor = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, jornada);

        spinnerJornada.setAdapter(adaptadorHor);

    }


}
