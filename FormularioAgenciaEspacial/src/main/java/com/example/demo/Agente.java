package com.example.demo;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Agente {
	@NotNull(message = "El nombre no puede estar vacío")
	@NotEmpty(message = "El nombre no puede estar vacío")
	private String nombre;
	@NotNull(message = "Debe seleccionar una nacionalidad")
	@NotEmpty(message = "Debe seleccionar una nacionalidad")
	private String nacionalidad;
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

	@Override
	public String toString() {
		return "Agente [nombre=" + nombre + ", nacionalidad=" + nacionalidad + ", planetas=" + planetas + "]";
	}
}
