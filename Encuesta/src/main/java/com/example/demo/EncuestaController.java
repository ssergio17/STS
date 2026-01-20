package com.example.demo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import entities.Category;
import jakarta.servlet.http.HttpSession;
import repositories.AnswerRepository;
import repositories.UserRepository;

@Controller
public class EncuestaController {
	UserRepository userRepository;
	AnswerRepository answerRepository;
	
	public EncuestaController(
			UserRepository userRepository,
			AnswerRepository answerRepository
			) {
		this.userRepository = userRepository;
		this.answerRepository = answerRepository;
	}
	
	@GetMapping("/")
	public String root() {
		return "name";
	}
	
	@PostMapping("/user")
	public String user(
			@RequestParam(name="user", required=true) String user,
			HttpSession session,
			Model model
			) {
		if(user == null || user.isBlank()) {
			model.addAttribute("errors", "El nombre es obligatorio");
			return "nombre";
		}
		
		session.setAttribute("user", user);
		session.setAttribute("points", 0);
		
		model.addAttribute("user", user);
		model.addAttribute("points", 0);
		
		return "pregunta1";
	}
	
	@PostMapping("/response")
	public String response(
			@RequestParam(name="optionScore", required=true) int optionScore,
			HttpSession session
			) {
		Integer currentPoints = (Integer) session.getAttribute("points");
		if (currentPoints == null) currentPoints = 0;
		
		session.setAttribute("points", optionScore + currentPoints);
		
		return "redirect:/resultado";
	}
	
	@GetMapping("/resultado")
	public String result(
			HttpSession session, 
			Model model
			) {
		String user = (String) session.getAttribute("user");
		Integer points = (Integer) session.getAttribute("points");
		Category category;
		
		if (points < 5) {
			category = Category.BAJA;
		} else if (points >= 5 || points <= 6) {
			category = Category.MEDIA;
		} else {
			category = Category.ALTA;
		}
		
		return "";
	}
}
