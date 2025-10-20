package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class FormularioControlador {
	
	@GetMapping("/formulario")
	public String MostrarFormulario(Model modelo) {
		modelo.addAttribute("persona", new Persona());
		return "formulario";
	}
	
	@PostMapping("/formulario")
	public String ValidarFormulario(@Valid @ModelAttribute("persona") Persona persona, BindingResult br, Model modelo) {
		System.out.println("Ejecutando POST");
		if(br.hasErrors()) {
			return "formulario";
		}
		
		modelo.addAttribute("imc", persona.getGrasaCorporal());
		modelo.addAttribute("persona", persona);
		return "resultado";
	}
}