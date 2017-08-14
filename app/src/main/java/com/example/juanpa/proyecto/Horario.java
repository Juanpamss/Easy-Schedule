package com.example.juanpa.proyecto;

import java.util.ArrayList;

public class Horario {

	private boolean[][] horario;
	private ArrayList<Materia> materiasAgregadas;

	Horario() {
		super();
		this.horario = new boolean[14][6];
		this.materiasAgregadas = new ArrayList<Materia>();
	}

	Horario(Horario horario) {
		this.horario = horario.getHorario();
		this.materiasAgregadas = horario.getMateriasAgregadas();
	}

	public boolean[][] getHorario() {
		return horario;
	}
	public void setHorario(boolean[][] horario) {
		this.horario = horario;
	}
	
	public ArrayList<Materia> getMateriasAgregadas() {
		return materiasAgregadas;
	}
	public void setMateriasAgregadas(ArrayList<Materia> materiasAgregadas) {
		this.materiasAgregadas = materiasAgregadas;
	}

	public boolean agregar(Materia materia) {
		for (int[] hora : materia.getHoras()) { // {dia, horaInicio, horaFin}
			for (int i = hora[1]; i < hora[2]; i++) {
				if (this.horario[i - 7][hora[0]]) {
					return false; // el horario se cruza
				}
				this.horario[i - 7][hora[0]] = true;
			}
		}
		this.materiasAgregadas.add(materia);
		return true; // el horario no tiene cruces
	}

	public boolean agregarMaterias(ArrayList<Materia> materias) { // implementa el backtracking recursivo
		for (Materia materia : materias) { // intenta agregar las materias una por una
			if (!materiasAgregadas.contains(materia)) {//si la materia aun no fue agregada
				Horario horario = new Horario(this);
				if (horario.agregar(materia)) { // la nueva materia no se cruza
					ArrayList<Materia> materiasRestantes = new ArrayList<Materia>(materias);
					materiasRestantes.remove(materia); // quita la nueva materia de la lista a agregar
					//si ya no hay mas materias que agregar
					if (materiasRestantes.isEmpty() || horario.agregarMaterias(materiasRestantes)) { 
						return true; // solucion
					}
				}
			}
		}
		return false;
	}

}
