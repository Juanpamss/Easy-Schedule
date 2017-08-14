package com.example.juanpa.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProspectoHorario extends Activity {

    ArrayList<Materia> materias;
    String grupo;
    boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int semestre = intent.getIntExtra("semestre", -1);
        Log.d("semestre", String.valueOf(semestre));
        String jornada = intent.getStringExtra("jornada");
        materias = new ArrayList<>();
        obtenerMaterias(semestre, jornada, new Callback() {
            @Override
            public void onSuccess(String response) {
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
                        materias.add(mat);
                        for (Materia m : materias) {
                            if (m.getNombre().equals(mat.getNombre()) && m.getGrupo().equals(mat.getGrupo())) {
                                m.agregarHora(hora);
                                break;
                            }
                        }
                    }
                    Log.d("status","response parsed");
                } catch (Throwable t) {
                }
            }
        });
        setContentView(new Calendario(this, materias));
    }

    public void obtenerMaterias(final int semestre, String jornada, final Callback callback) {
        if (jornada.equals("Matutina")) {
            grupo = "GR1";
        } else {
            grupo = "GR2";
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url2 = "http://10.0.2.2/horarios.php";
        StringRequest jsRequest = new StringRequest
                (Request.Method.POST, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("response", response.toString());
                            callback.onSuccess(response);
                        } catch (Throwable t) {
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProspectoHorario.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("semestre", String.valueOf(semestre));
                params.put("jornada", grupo);
                return params;
            }
        };
        queue.add(jsRequest);
    }
}
