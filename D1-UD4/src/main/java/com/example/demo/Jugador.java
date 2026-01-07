package com.example.demo;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Jugador {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL)
	private List<Quiz> partidas;
	
	public Jugador() {}
	public Jugador(String name) {
		this.name = name;
	}
	
	// Getters
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Quiz> getPartidas() {
		return partidas;
	}
	
	// Setters
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPartidas(List<Quiz> partidas) {
		this.partidas = partidas;
	}
	
}
