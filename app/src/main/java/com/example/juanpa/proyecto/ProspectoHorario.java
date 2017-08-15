package com.example.juanpa.proyecto;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProspectoHorario extends Activity {

    ArrayList<Materia> materias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("materias", null);
        Type type = new TypeToken<ArrayList<Materia>>() {}.getType();
        materias = gson.fromJson(json, type);

        ScrollView scroll = new ScrollView(this);

        LinearLayout viewGroup = new LinearLayout(this);
        viewGroup.setOrientation(LinearLayout.VERTICAL);

        scroll.addView(viewGroup);
        setContentView(scroll);

        viewGroup.addView(new Calendario(ProspectoHorario.this, materias));

        Button myButton = new Button(this);
        myButton.setText("Guardar");
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
        viewGroup.addView(myButton);
    }

    public void guardar() {
        SharedPreferences sharedPrefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(materias);
        editor.putString("materias", json);
        editor.commit();
    }
}
