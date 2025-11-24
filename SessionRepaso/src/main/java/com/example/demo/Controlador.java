package com.example.demo;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class Controlador {
	private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	private static Map<String, String> USERS = new HashMap<>();
	
	public Controlador() {
		USERS.put("Sergio", "$2a$10$NLMo0ePjCeXVJsM.dnqPKeemVQlNAFD.ACYHTcnHBp3tCf18GgVme");
		USERS.put("Admin", "$2a$10$Tkz9dd0leWbM6MeyZl/6deIIixjG79zjVxOHOCZ/C11ny.Rop997K");
	}
	
	@GetMapping("/")
	public String Root(
		HttpSession session,
		Model model
		) {
		if(NotLogged(session)) return "redirect:/login";
		model.addAttribute("user", session.getAttribute("user"));
		return "home";
	}
	
	@GetMapping("/login")
	public String GetLogin() {
		return "login";
	}
	
	@PostMapping("/login")
	public String PostLogin(
		@RequestParam(name="user", required=true) String user,
		@RequestParam(name="password", required=true) String password,
		HttpSession session,
		Model model
		) {
		String hash = USERS.get(user);
		
		if(hash != null) {
			if(encoder.matches(password, hash)) {
				session.setAttribute("user", user);
				model.addAttribute("user", session.getAttribute("user"));
				return "home";
			}
		}
		model.addAttribute("errors", "Usuario o contraseña no válidos");
		return "login";
	}
	
	@GetMapping("/home")
	public String Home(
		HttpSession session,
		Model model
		) {
		if(NotLogged(session)) return "redirect:/login";
		model.addAttribute("user", session.getAttribute("user"));
		return "home";
	}
	
	// Métodos auxiliares
	private boolean NotLogged(HttpSession session) {
		return session.getAttribute("user") == null;
	}
}
