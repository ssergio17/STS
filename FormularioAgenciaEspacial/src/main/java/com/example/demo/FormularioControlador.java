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
	public String Inicio(@ModelAttribute Agente formAgente, Model modelo) {
		modelo.addAttribute("agente", agente);
		return "nombre";
	}
	
	@PostMapping("/nombre")
	public String FormularioNombre(@ModelAttribute Agente formAgente) {
		agente.setNombre(formAgente.getNombre());
		return "nacionalidad";
	}
	
	@GetMapping("/nacionalidad")
	public String InicioNacionalidad(@ModelAttribute Model modelo) {
		modelo.addAttribute("agente", agente);
		return "nacionalidad";
	}
	
	@PostMapping("/nacionalidad")
	public String FormularioNacionalidad(@ModelAttribute Agente formAgente) {
		agente.setNacionalidad(formAgente.getNacionalidad());
		return "planetas";
	}
	
	@GetMapping("/planetas")
	public String InicioPlanetas(@ModelAttribute Model modelo) {
		modelo.addAttribute("agente", agente);
		return "planetas";
	}
	
	@PostMapping("/planetas")
	public String FormularioPlanetas(@ModelAttribute Agente formAgente, Model modelo) {
		agente.setPlanetas(formAgente.getPlanetas());
		modelo.addAttribute("agente", agente);
		return "resultado";
	}
}