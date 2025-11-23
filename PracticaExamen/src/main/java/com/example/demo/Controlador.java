package com.example.demo;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
@SessionAttributes("user")
public class Controlador {
	private static Map<String, String> USERS;
	
	public Controlador() {
		USERS = new HashMap();
		USERS.put("Sergio", "1234");
	}
	
	@GetMapping("/")
	public String Root(HttpSession session) {
		if(NotLogged(session)) return "redirect:/login";
		return "redirect:/inicio";
	}

	@GetMapping("/login")
	public String GetLogin(HttpSession session) {
		if(!NotLogged(session)) return "redirect:/inicio";
		return "login";
	}
	
	@PostMapping("/login")
	public String ProccessLogin(
		@RequestParam(name="user", required=true) String user,
		@RequestParam(name="password", required=true) String password,
		Model model
		) {
		String errors = "";
		String hash = USERS.get(user);
		
		if(hash != null) {
			if(hash.equals(password)) {
				model.addAttribute("user", user);
				return "redirect:/pagina1";
			}
		}
		errors = "Usuario o contraseña no válidos";
		model.addAttribute("errors", errors);
		return "login";
	}
	
	@GetMapping("/pagina1")
	public String GetPagina1(HttpSession session, Model model) {
		if(NotLogged(session)) return "redirect:/login";
		return "pagina1";
	}
	
	@GetMapping("/pagina2")
	public String GetPagina2(HttpSession session, Model model) {
		if(NotLogged(session)) return "redirect:/login";
		return "pagina2";
	}
	
	// Métodos auxiliares
	private boolean NotLogged(HttpSession session) {
		return session.getAttribute("user") == null;
	}
	
}
