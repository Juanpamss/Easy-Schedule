package com.example.juanpa.proyecto;

import android.os.Bundle;
import android.app.Activity;

public class ProspectoHorario extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Calendario(this));
    }
}
