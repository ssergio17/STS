package com.example.demo;
import java.util.List;
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
@RequestMapping("/personajes")
public class apiController {
	private final PersonajeService service;

    public apiController(PersonajeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Personaje> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Personaje findById(@PathVariable("id") Long id) {
        return service.findById(id).orElse(null);
    }

    @PostMapping
    public Personaje create(@RequestBody Personaje personaje) {
        return service.save(personaje);
    }

    @PutMapping("/{id}")
    public Personaje update(@PathVariable("id") Long id, @RequestBody Personaje personaje) {
        personaje.setId(id);
        return service.save(personaje);
    }

    @PatchMapping("/{id}")
    public Personaje patch(@PathVariable("id") Long id, @RequestBody Personaje personaje) {
        return service.patch(id, personaje).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }
}
