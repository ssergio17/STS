package com.example.demo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

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
			HttpServletRequest request,
			Model model
			
			) throws UnsupportedEncodingException {
		
		String errores = "";
		
		if(etapaVolver != null) {
			switch(etapaVolver) {
				case "volverNombre":
					model.addAttribute("nombreAgente", nombreAgente);
					return "nombre";
				case "volverNacionalidad":
					model.addAttribute("nombreAgente", nombreAgente);
					model.addAttribute("nacionalidadAgente", nacionalidadAgente);
					return "nacionalidad";
				case "volverPlanetas":
					model.addAttribute("nombreAgente", nombreAgente);
					model.addAttribute("nacionalidadAgente", nacionalidadAgente);
					model.addAttribute("planetasAgente", planetasAgente);
					return "planetas";
			}
		}
		
		if("nombre".equals(etapa)) {
			// Caracteres especiales
			System.out.println("Prueba de POST");
			System.out.println("Texto recibido: " + nombreAgente);
			System.out.println("Content-Type: " + request.getContentType());
			System.out.println("Encoding: " + request.getCharacterEncoding());
			System.out.println("-----------------------------------------------");
			
			// Codificación de URL
			System.out.println("Prueba de codificación con (POST)");
		    System.out.println("Método HTTP: " + request.getMethod());
		    System.out.println("Content-Type: " + request.getContentType());
		    System.out.println("Character Encoding: " + request.getCharacterEncoding());
		    System.out.println("-----------------------------------------------");
		    
		    if (nombreAgente != null) {
		        String codificado = URLEncoder.encode(nombreAgente, StandardCharsets.UTF_8);
		        System.out.println("Valor original: " + nombreAgente);
		        System.out.println("Codificado con URLEncoder: " + codificado);
		    }
		    
			if(nombreAgente != null && !nombreAgente.isBlank()) {
				model.addAttribute("nombreAgente", nombreAgente);
				return "nacionalidad";
			}else {
				errores = "El nombre no puede estar vacío";
				model.addAttribute("nombreAgente", nombreAgente);
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
				model.addAttribute("nombreAgente", nombreAgente);
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
				model.addAttribute("nombreAgente", nombreAgente);
				model.addAttribute("errores", errores);
				return "planetas";
			}
		}
		
		return "nombre";
	}
	
}