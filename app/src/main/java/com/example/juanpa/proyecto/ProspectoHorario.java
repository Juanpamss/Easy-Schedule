package com.example.juanpa.proyecto;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProspectoHorario extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("materias", null);
        Type type = new TypeToken<ArrayList<Materia>>() {}.getType();
        ArrayList<Materia> materias = gson.fromJson(json, type);
        setContentView(new Calendario(ProspectoHorario.this, materias));
    }
}
