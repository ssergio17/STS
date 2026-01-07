package com.example.demo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JugadorRepository extends JpaRepository<Jugador, Long>{
	Jugador findByName(String name);
}
