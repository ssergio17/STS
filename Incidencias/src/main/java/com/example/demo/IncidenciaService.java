package com.example.demo;
import java.util.List;
import java.util.Optional;

public interface IncidenciaService {
	List<Incidencia> findAll();
	Optional<Incidencia> findById(Long id);
	Incidencia save(Incidencia incidencia);
	boolean exists(Incidencia incidencia);
	boolean existsById(Long id);
	void deleteById(Long id);
	Optional<Incidencia> patch(Long id, Incidencia incidencia);
}
