package com.example.demo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FormularioControlador {
	
	static Agente agente = new Agente();
	
	@GetMapping("/nombre")
	public String Inicio(Agente agente, Model modelo) {
		modelo.addAttribute("agente", agente);
		return "nombre";
	}
	
	@PostMapping("/nombre")
	public String FormularioNombre(Agente agente, Model modelo) {
		modelo.addAttribute("agente", agente);
		return "nacionalidad";
	}
	
	@PostMapping("/nacionalidad")
	public String FormularioNacionalidad(Agente agente, Model modelo) {
		modelo.addAttribute("agente", agente);
		return "planetas";
	}
	
	@PostMapping("/planetas")
	public String FormularioPlanetas(Agente agente, Model modelo) {
		modelo.addAttribute("agente", agente);
		return "resultado";
	}
}