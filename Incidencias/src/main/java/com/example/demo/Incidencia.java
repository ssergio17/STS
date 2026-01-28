package com.example.demo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Incidencia {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String titulo, descripcion, prioridad, estado;
	
	public Incidencia() {}
	public Incidencia(String titulo, String descripcion, String prioridad, String estado) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.prioridad = prioridad;
		this.estado = estado;
	}
	
	// Getters
	public Long getId() {
		return id;
	}
	public String getTitulo() {
		return titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public String getPrioridad() {
		return prioridad;
	}
	public String getEstado() {
		return estado;
	}
	
	// Setters
	public void setId(Long id) {
		this.id = id;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
