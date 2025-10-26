package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class Agente {
	private String nombre, nacionalidad;
	private List<String> planetas = new ArrayList<>();
	
	public Agente() {}

	// Getters
	public String getNombre() {
		return nombre;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public List<String> getPlanetas() {
		return planetas;
	}

	// Setters
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public void setPlanetas(List<String> planetas) {
		this.planetas = planetas;
	}
	
}
