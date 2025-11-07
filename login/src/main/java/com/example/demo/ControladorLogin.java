package com.example.demo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControladorLogin {
	
	@GetMapping("/")
	public String Inicio() {
		return "";
	}
	
	@PostMapping("/")
	public String ProcesarDatos() {
		return "";
	}
	
}