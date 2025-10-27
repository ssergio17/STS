package com.example.demo;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FormularioControlador {
	
	@GetMapping("/")
	public String Formulario() {
		return "nombre";
	}
	
	@PostMapping("/")
	public String ProcesarDatos(
			
			@RequestParam(name = "nombreAgente", required = false) String nombreAgente,
			@RequestParam(name = "nacionalidadAgente", required = false) String nacionalidadAgente,
			@RequestParam(name = "planetasAgente", required = false) List<String> planetasAgente,
			@RequestParam(name = "etapa", required = false) String etapa,
			@RequestParam(name = "etapaVolver", required = false) String etapaVolver,
			Model model
			
			) {
		
		String errores = "";
		
		if("volverNombre".equals(etapaVolver)) {
			model.addAttribute("nombreAgente", nombreAgente);
			return "nombre";
		}
		
		if("volverNacionalidad".equals(etapaVolver)) {
			model.addAttribute("nombreAgente", nombreAgente);
			model.addAttribute("nacionalidadAgente", nacionalidadAgente);
			return "nacionalidad";
		}
		
		if("volverPlanetas".equals(etapaVolver)) {
			model.addAttribute("nombreAgente", nombreAgente);
			model.addAttribute("nacionalidadAgente", nacionalidadAgente);
			model.addAttribute("planetasAgente", planetasAgente);
			return "planetas";
		}
		
		if("nombre".equals(etapa)) {
			if(nombreAgente != null && !nombreAgente.isBlank()) {
				model.addAttribute("nombreAgente", nombreAgente);
				return "nacionalidad";
			}else {
				errores = "El nombre no puede estar vacío";
				model.addAttribute("errores", errores);
				return "nombre";
			}
		}else if("nacionalidad".equals(etapa)) {
			if(nacionalidadAgente != null && !nacionalidadAgente.isBlank()) {
				model.addAttribute("nombreAgente", nombreAgente);
				model.addAttribute("nacionalidadAgente", nacionalidadAgente);
				return "planetas";
			}else {
				errores = "Debe seleccionar una nacionalidad";
				model.addAttribute("errores", errores);
				return "nacionalidad";
			}			
		}else if("planetas".equals(etapa)) {
			if(planetasAgente != null && !planetasAgente.isEmpty()) {
				model.addAttribute("nombreAgente", nombreAgente);
				model.addAttribute("nacionalidadAgente", nacionalidadAgente);
				model.addAttribute("planetasAgente", planetasAgente);
				return "resultados";
			}else {
				errores = "Debe seleccionar al menos un planeta";
				model.addAttribute("errores", errores);
				return "planetas";
			}
		}
		
		return "nombre";
	}
	
}