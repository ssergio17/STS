package com.example.demo;
import java.util.List;
import java.util.Optional;

public class IncidenciaServiceImplementation implements IncidenciaService{
	private final IncidenciaRepository repo; 
	
	public IncidenciaServiceImplementation(IncidenciaRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<Incidencia> findAll() {
		return repo.findAll();
	}

	@Override
	public Optional<Incidencia> findById(Long id) {
		return repo.findById(id);
	}

	@Override
	public Incidencia save(Incidencia incidencia) {
		return repo.save(incidencia);
	}

	@Override
	public boolean existsById(Long id) {
		return repo.existsById(id);
	}

	@Override
	public void deleteById(Long id) {
		repo.deleteById(id);
	}

	@Override
	public Optional<Incidencia> patch(Long id, Incidencia incidencia) {
		Optional<Incidencia> existente = repo.findById(id);
		
		if(existente.isEmpty()) {
			return Optional.empty();
		}
		
		Incidencia in = existente.get();
		
		if(incidencia.getTitulo() != null) {
			in.setTitulo(incidencia.getTitulo());
		}
		
		if(incidencia.getDescripcion() != null) {
			in.setTitulo(incidencia.getDescripcion());
		}
		
		if(incidencia.getPrioridad() != null) {
			in.setTitulo(incidencia.getPrioridad());
		}
		
		if(incidencia.getEstado() != null) {
			in.setTitulo(incidencia.getEstado());
		}
		
		return Optional.of(repo.save(in));
	}

	@Override
	public boolean exists(Incidencia incidencia) {
		return repo.exists(incidencia);
	}
	
}
