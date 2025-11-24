package com.example.demo;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class Controlador {
	@GetMapping("/")
	public String Root() {
		return "name";
	}
	
	@GetMapping("/name")
	public String GetName(
		HttpServletRequest request,
		Model model
		) {
		String user = GetCookie(request, "user");
		if(user != null) {
			model.addAttribute("user", user);
		}
		return "name";
	}
	
	@GetMapping("/nacionality")
	public String GetNacionality(
		HttpServletRequest request,
		Model model
		) {
		String nacionality = GetCookie(request, "nacionality");
		if(nacionality != null) {
			model.addAttribute("nacionality", nacionality);
		}
		return "nacionality";
	}
	
	@GetMapping("/planets")
	public String GetPlanets(
		HttpServletRequest request,
		Model model
		) {
		String planets = GetCookie(request, "planets");
		if(planets != null) {
			List<String> p = Arrays.asList(planets.split("|"));
			model.addAttribute("planets", p);
		}
		return "planets";
	}
	
	@PostMapping("/name")
	public String SaveName(
		@RequestParam(name="user", required=true) String user,
		HttpServletRequest request,
		HttpServletResponse response,
		Model model
		) {
		if(user != null && !user.isBlank()) {
			Cookie cookie = new Cookie("user", user);
			cookie.setPath("/");
			response.addCookie(cookie);
			model.addAttribute("user", user);
			return "nacionality";
		}
		model.addAttribute("errors", "Nombre no válido");
		return "name";
	}
	
	@PostMapping("/planets")
	public String SavePlanets(
		@RequestParam(name="planets", required=true) List<String> planets,
		HttpServletRequest request,
		HttpServletResponse response,
		Model model
		) {
		if(planets != null && !planets.isEmpty()) {
			Cookie cookie = new Cookie("planets", String.join("|", planets));
			cookie.setPath("/");
			response.addCookie(cookie);
			model.addAttribute("user", GetCookie(request, "user"));
			model.addAttribute("nacionality", GetCookie(request, "nacionality"));
			model.addAttribute("planets", planets);
			return "results";
		}
		model.addAttribute("errors", "Debe seleccionar al menos un planeta");
		return "planets";
	}
	
	@PostMapping("/nacionality")
	public String Nacionality(
		@RequestParam(name="nacionality", required=true) String nacionality,
		HttpServletRequest request,
		HttpServletResponse response,
		Model model
		) {
		if(nacionality != null && !nacionality.isBlank()) {
			Cookie cookie = new Cookie("nacionality", nacionality);
			cookie.setPath("/");
			response.addCookie(cookie);
			model.addAttribute("nacionality", nacionality);
			model.addAttribute("user", GetCookie(request, "user"));
			return "planets";
		}
		model.addAttribute("errors", "La nacionalidad no puede estar vacía");
		return "nacionality";
	}
	
	// Métodos auxiliares
	private String GetCookie(
			HttpServletRequest request,
			String name
			) {
		if(request.getCookies() != null) {
			for(Cookie c : request.getCookies()) {
				if(c.getName().equals(name)) {
					return c.getValue();
				}
			}
		}
		return null;
	}
}
