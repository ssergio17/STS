package com.example.demo;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class QuizController {
	private final QuizRepository quizRepository;
	private final JugadorRepository jugadorRepository;
	
	public QuizController(
		QuizRepository quizRepository, 
		JugadorRepository jugadorRepository
		) {
		this.quizRepository = quizRepository;
		this.jugadorRepository = jugadorRepository;
	}
	
	@GetMapping("/")
	public String quiz() {
		return "inicio";
	}
	
	@PostMapping("/start")
	public String inicio(
	        @RequestParam(name="name", required=true) String name,
	        HttpSession session
	) {
	    session.setAttribute("name", name);
	    session.setAttribute("points", 0);
	    session.setAttribute("questionIndex", 0);

	    return "redirect:/pregunta";
	}
	
	@GetMapping("/pregunta")
	public String pregunta(HttpSession session, Model model) {
	    Integer index = (Integer) session.getAttribute("questionIndex");
	    if (index == null) {
	        index = 0;
	        session.setAttribute("questionIndex", 0);
	    }

	    model.addAttribute("index", index);
	    return "pregunta";
	}
	
	@PostMapping("/respuesta")
	public String respuesta(
	        @RequestParam(name="points") int points,
	        HttpSession session
	) {
	    Integer total = (Integer) session.getAttribute("points");
	    Integer index = (Integer) session.getAttribute("questionIndex");

	    session.setAttribute("points", total + points);
	    session.setAttribute("questionIndex", index + 1);

	    if (index + 1 >= 3) {
	        return "redirect:/resultado";
	    }

	    return "redirect:/pregunta";
	}
	
	@GetMapping("/resultado")
	public String finalizar(HttpSession session, Model model) {
		String name = (String) session.getAttribute("name");
		Integer points = (Integer) session.getAttribute("points");
		
		if (name == null || points == null) {
			return "redirect:/";
		}
		
		Jugador jugador = jugadorRepository.findByName(name);
		if (jugador == null) {
			jugador = new Jugador(name);
			jugadorRepository.save(jugador);
		}
		
		Categoria categoria;
		if (points < 5) {
			categoria = Categoria.BAJA;
		}else if(points < 8) {
			categoria = Categoria.MEDIA;
		}else {
			categoria = Categoria.ALTA;
		}
		
		Quiz quiz = new Quiz();
		quiz.setJugador(jugador);
		quiz.setPoints(points);
		quiz.setDate(LocalDateTime.now());
		quiz.setCategoria(categoria);
		
		quizRepository.save(quiz);
		
		model.addAttribute("name", name);
		model.addAttribute("points", points);
		model.addAttribute("categoria", categoria);
		
		session.invalidate();
		
		return "resultado";
	}
	
	@GetMapping("/informe")
	public String informe (
		@RequestParam(name="minPoints", required = false) Integer minPoints,
		Model model
		) {
		List<Quiz> quizzes;
		
		if (minPoints != null) {
			quizzes = quizRepository.findByPointsGreaterThan(minPoints);
		} else {
			quizzes = quizRepository.findAllByOrderByPointsDesc();
		}
		
		model.addAttribute("quizzes", quizzes);
		return "informe";
	}
	
	@GetMapping("/top5")
	public String top5 (Model model) {
		List<Quiz> top = quizRepository.findTop5ByOrderByPointsDesc();
		model.addAttribute("quizzes", top);
		return "informe";
	}
	
	@GetMapping("/borrar")
	public String borrar(@RequestParam(name="id") Long id) {
		quizRepository.deleteById(id);
		return "redirect:/informe";
	}
}
