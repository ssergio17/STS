package com.example.demo;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PersonajeServiceImplementation implements PersonajeService{

	  private final PersonajeRepository repository;

	    public PersonajeServiceImplementation(PersonajeRepository repository) {
	        this.repository = repository;
	    }

	    @Override
	    public List<Personaje> findAll() {
	        return repository.findAll();
	    }

	    @Override
	    public Optional<Personaje> findById(Long id) {
	        return repository.findById(id);
	    }

	    @Override
	    public Personaje save(Personaje personaje) {
	        return repository.save(personaje);
	    }

	    @Override
	    public boolean exists(Personaje personaje) {
	        if (personaje.getName() == null) {
	            return false;
	        }
	        return repository.existsByName(personaje.getName());
	    }

	    @Override
	    public boolean existsById(Long id) {
	        return repository.existsById(id);
	    }

	    @Override
	    public void deleteById(Long id) {
	        repository.deleteById(id);
	    }

	    @Override
	    public Optional<Personaje> patch(Long id, Personaje datosParciales) {

	        Optional<Personaje> existenteOpt = repository.findById(id);

	        if (existenteOpt.isEmpty()) {
	            return Optional.empty();
	        }

	        Personaje existente = existenteOpt.get();

	        if (datosParciales.getName() != null) {
	            existente.setName(datosParciales.getName());
	        }
	        if (datosParciales.getJob() != null) {
	            existente.setJob(datosParciales.getJob());
	        }
	        if (datosParciales.getGender() != null) {
	            existente.setGender(datosParciales.getGender());
	        }
	        if (datosParciales.getStatus() != null) {
	            existente.setStatus(datosParciales.getStatus());
	        }

	        return Optional.of(repository.save(existente));
	    }

}
