package com.example.juanpa.proyecto;

import java.util.ArrayList;

public class ObjetoCabecera {

    String header1;
    String header2;
    String header3;
    String header4;
    String header5;
    String header6;
    String header7;


    public ObjetoCabecera(Materia materia){

        this.header1 = "\n" + materia.getNombre() + "\n";
        for(int[] hora : materia.getHoras()){
            switch (hora[0]) {
                case 0:
                    this.header2 = "" + hora[1] + " - " + hora[2] + "";
                    break;
                case 1:
                    this.header3 = "" + hora[1] + " - " + hora[2] + "";
                    break;
                case 2:
                    this.header4 = "" + hora[1] + " - " + hora[2] + "";
                    break;
                case 3:
                    this.header5 = "" + hora[1] + " - " + hora[2] + "";
                    break;
                case 4:
                    this.header6 = "" + hora[1] + " - " + hora[2] + "";
                    break;
                case 5:
                    this.header7 = "" + hora[1] + " - " + hora[2] + "";
                    break;
            }
        }
        /*
        this.header2 = header2;
        this.header3 = header3;
        this.header4 = header4;
        this.header5 = header5;
        this.header6 = header6;
        this.header7 = header7;
        */
    }
}