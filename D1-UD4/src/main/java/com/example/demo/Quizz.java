package com.example.demo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Quizz {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Integer points;
	
	public Quizz() {}
	public Quizz(Long id, String name, Integer points) {
		this.id = id;
		this.name = name;
		this.points = points;
	}
	
	// Getters
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Integer getPoints() {
		return points;
	}
	
	// Setters
	public void setName(String name) {
		this.name = name;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
}
