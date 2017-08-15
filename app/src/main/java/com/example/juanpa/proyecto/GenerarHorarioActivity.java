package com.example.juanpa.proyecto;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GenerarHorarioActivity extends AppCompatActivity {

    private ArrayList<String> listaMaterias = new ArrayList<>();
    private ListView lstView;
    private ArrayAdapter<String> adapter;

    ArrayList<Materia> materiasEncontradas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_horario);

        materiasEncontradas = new ArrayList<>();

        lstView = (ListView) findViewById(R.id.mainListView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaMaterias);
        lstView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //mostrar un mensaje para confirmar la eliminacion
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                String removida = listaMaterias.remove(position);
                                for (int i = materiasEncontradas.size() - 1; i >= 0; i--) {
                                    if (materiasEncontradas.get(i).getNombre().equals(removida)) {
                                        materiasEncontradas.remove(i);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(GenerarHorarioActivity.this);
                builder.setMessage("¿Quitar " + listaMaterias.get(position) +" de la lista?").setPositiveButton("Sí", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }

    public void listaDeMaterias(View view) {
        Intent intent = new Intent(this, ListaDeMateriasActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String mat = data.getStringExtra("materia");
                consultarMateria(mat);
                listaMaterias.add(mat);
                adapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public void prospectoHorario(View view) {
        //guardar la lista de materias
        SharedPreferences sharedPrefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(materiasEncontradas);
        editor.putString("materias", json);
        editor.commit();
        Intent intent = new Intent(this, ProspectoHorario.class);
        startActivity(intent);
    }

    public void consultarMateria(final String nombre) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url2 = "http://10.0.2.2/materia.php";
        StringRequest jsRequest = new StringRequest
                (Request.Method.POST, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("response", response);

                            try {
                                JSONArray array = new JSONArray(response);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject o = array.getJSONObject(i);
                                    Materia mat = new Materia(o.getString("NOMBRE"), o.getString("GRUPO"));
                                    int dia = 0;
                                    switch (o.getString("DIA")) {
                                        case "Lunes":
                                            dia = 0;
                                            break;
                                        case "Martes":
                                            dia = 1;
                                            break;
                                        case "Miercoles":
                                            dia = 2;
                                            break;
                                        case "Jueves":
                                            dia = 3;
                                            break;
                                        case "Viernes":
                                            dia = 4;
                                            break;
                                        case "Sabado":
                                            dia = 5;
                                            break;
                                    }
                                    int[] hora = {dia, o.getInt("HORA_INICIO"), o.getInt("HORA_FIN")};
                                    boolean agregada = false;
                                    for (Materia m : materiasEncontradas) {
                                        if (m.getNombre().equals(mat.getNombre()) && m.getGrupo().equals(mat.getGrupo())) {
                                            m.agregarHora(hora);
                                            agregada = true;
                                            break;
                                        }
                                    }
                                    if (!agregada) {
                                        materiasEncontradas.add(mat);
                                        mat.agregarHora(hora);
                                    }
                                }
                                Log.d("status", "response parsed");
                            } catch (Throwable t) {
                            }

                        } catch (Throwable t) {
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GenerarHorarioActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nombre", nombre);
                return params;
            }
        };
        queue.add(jsRequest);
    }
}
