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

        this.header1 = "\n\n" + materia.getNombre() + "\n\n";
        for(int[] hora : materia.getHoras()){
            switch (hora[0]) {
                case 0:
                    this.header2 = "\n\n" + hora[1] + " - " + hora[2] + "\n\n";
                    break;
                case 1:
                    this.header3 = "\n\n" + hora[1] + " - " + hora[2] + "\n\n";
                    break;
                case 2:
                    this.header4 = "\n\n" + hora[1] + " - " + hora[2] + "\n\n";
                    break;
                case 3:
                    this.header5 = "\n\n" + hora[1] + " - " + hora[2] + "\n\n";
                    break;
                case 4:
                    this.header6 = "\n\n" + hora[1] + " - " + hora[2] + "\n\n";
                    break;
                case 5:
                    this.header7 = "\n\n" + hora[1] + " - " + hora[2] + "\n\n";
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