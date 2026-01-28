package com.example.demo;
import java.util.List;
import java.util.Optional;

public interface PersonajeService {
	List<Personaje> findAll();
    Optional<Personaje> findById(Long id);
    Personaje save(Personaje personaje);
    boolean exists(Personaje personaje);
    boolean existsById(Long id);
    void deleteById(Long id);
    Optional<Personaje> patch(Long id, Personaje datosParciales);
}
