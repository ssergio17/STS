package com.example.demo;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Quiz {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "jugador_id")
	private Jugador jugador;
	private Integer points;
	private LocalDateTime date;
	
	@Enumerated(EnumType.STRING)
	private Categoria categoria;
	
	public Quiz() {}
	
	// Getters
	public Long getId() {
		return id;
	}
	
	public Integer getPoints() {
		return points;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	public Jugador getJugador() {
		return jugador;
	}
	
	// Setters
	public void setPoints(Integer points) {
		this.points = points;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
}
