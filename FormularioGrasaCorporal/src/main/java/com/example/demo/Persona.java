package com.example.demo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class Persona {
	
	@NotNull
	private String nombre;
	
	@NotNull
	@Pattern(regexp = "Hombre|Mujer", message = "Debe ser hombre o mujer")
	private String genero;
	
	@NotNull
	private int edad;
	
	@NotNull
	private double peso, altura;
	private final double N1 = 1.20, N2 = 0.23, N3 = 10.8, N4 = 5.4;
	
	private double imc;
	
	// Getters
	public String getNombre() {
		return nombre;
	}
	
	public String getGenero() {
		return genero;
	}
	
	public int getEdad() {
		return edad;
	}
	
	public double getPeso() {
		return peso;
	}
	
	public double getIMC() {
		double alturaMetros = altura / 100;
		return peso/(alturaMetros * alturaMetros);
	}
	
	public double getAltura() {
		return altura;
	}
	
	public double getGrasaCorporal() {
		int gn = (genero.equals("Hombre")) ? 1  : 2;
		double gc = (N1 * getIMC()) + (N2 * edad) - (N3 * gn) - N4;
		return Math.round(gc * 100.0) / 100.0;
	}
	
	// Setters
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	public void setEdad(int edad) {
		this.edad = edad;
	}
	
	public void setPeso(double peso) {
		this.peso = peso;
	} 
	
	public void setAltura(double altura) {
		this.altura = altura;
	}
	
}
