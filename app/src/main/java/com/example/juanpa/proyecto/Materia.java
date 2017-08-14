package com.example.juanpa.proyecto;

import java.util.ArrayList;

public class Materia {

	private String nombre;
	private String grupo;
	private ArrayList<int []> horas; //dia (0 = lunes, 1 = martes, etc), horaInicio, horaFin. Ej: {{0,7,9},{1,9,11},...}
		
	public Materia(String nombre, String grupo) {
		super();
		this.nombre = nombre;
		this.grupo = grupo;
		this.horas = new ArrayList<>();
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public ArrayList<int []> getHoras() {
		return horas;
	}
	public void setHoras(ArrayList<int []> horas) {
		this.horas = horas;
	}
	public void agregarHora(int[] hora) { this.horas.add(hora); }
}
