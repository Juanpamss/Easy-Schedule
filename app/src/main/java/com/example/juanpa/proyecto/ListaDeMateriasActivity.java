package com.example.juanpa.proyecto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListaDeMateriasActivity extends AppCompatActivity {

    Spinner spinner, spinnerSemestre;
    ArrayList<String> semestre = new ArrayList<String>();
    ArrayList<String> carrera;

    private ListView lstView;
    private ArrayAdapter<String> adapter;

    ArrayList<String> materias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_materias);

        spinner = (Spinner) findViewById(R.id.spinnerCarrera);
        spinnerSemestre = (Spinner) findViewById(R.id.spinnerSemestre);
        //llenar spinner carrera
        carrera = new ArrayList<String>();
        carrera.add("I.S");
        ArrayAdapter adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, carrera);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adaptador);

        llenarSemestres();

        lstView = (ListView)findViewById(R.id.mainListView);
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //retornar la materia
                Intent returnIntent = new Intent();
                returnIntent.putExtra("materia", lstView.getItemAtPosition(position).toString());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
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

    public void llenarLista(View view){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url2 = "http://10.0.2.2/materias.php";
        StringRequest jsRequest = new StringRequest
                (Request.Method.POST, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("response", response);
                            try {
                                materias = new ArrayList<>();
                                JSONArray array = new JSONArray(response);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject o = array.getJSONObject(i);
                                    materias.add(o.getString("NOMBRE"));
                                }
                                adapter = new ArrayAdapter<String>(ListaDeMateriasActivity.this,android.R.layout.simple_list_item_1,materias);
                                lstView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } catch (Throwable t) {
                            }

                        } catch (Throwable t) {
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListaDeMateriasActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("semestre", String.valueOf(spinnerSemestre.getSelectedItemPosition() + 1));
                return params;
            }
        };
        queue.add(jsRequest);
    }
}
