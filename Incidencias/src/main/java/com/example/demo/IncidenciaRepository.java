package com.example.demo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Long>{
	boolean exists(Incidencia incidencia);
}
