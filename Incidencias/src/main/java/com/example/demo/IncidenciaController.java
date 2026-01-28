package com.example.demo;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/incidencias")
public class IncidenciaController {
	private IncidenciaService service;
	public IncidenciaController(IncidenciaService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<List<Incidencia>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Incidencia> findById(@PathVariable("id") Long id) {
		return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<Incidencia> create(@RequestBody Incidencia incidencia) {
		if(service.exists(incidencia)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
		Incidencia in = service.save(incidencia);
		return ResponseEntity.status(HttpStatus.CREATED).body(in);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Incidencia> put(@PathVariable("id") Long id, @RequestBody Incidencia incidencia) {
		if(!service.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		incidencia.setId(id);
		Incidencia updated = service.save(incidencia);
		return ResponseEntity.ok(updated);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Incidencia> patch(@PathVariable("id") Long id, @RequestBody Incidencia incidencia) {
		
		if(incidencia.getId() != null && incidencia.getId().equals(id)) {
			return ResponseEntity.badRequest().build();
		}
		
		return service.patch(id, incidencia).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		if(service.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}
