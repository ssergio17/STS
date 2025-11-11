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
public class ControladorLogin {
	
	private static final Map<String, String> USERS = new HashMap<>();
	private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@GetMapping("/")
	public String Root(HttpSession session) {
		if(!logged(session)) return "login";
		return "pagina1";
	}
	
	@GetMapping("/pagina1")
	public String GetPage1(HttpSession session, Model model) {
		if(!logged(session)) return "login";
		model.addAttribute("user", session.getAttribute("user"));
		return "pagina1";
	}
	
	@GetMapping("/pagina2")
	public String GetPage2(HttpSession session, Model model) {
		if(!logged(session)) return "login";
		model.addAttribute("user", session.getAttribute("user"));
		return "pagina2";
	}
	
	@GetMapping("/login")
	public String GetLogin(HttpSession session) {
		if(logged(session)) return "pagina1";
		return "login";
	}
	
	@GetMapping("/registro")
	public String GetRegister() {
		return "registro";
	}
	
	@GetMapping("/logout")
	public String Logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
	
	@PostMapping("/login") 
	public String Login(
			@RequestParam(name="user", required=true) String user,
			@RequestParam(name="password", required=true) String password,
			HttpSession session,
			Model model
			) {
		System.out.println(user);
		String hash = USERS.get(user);
		
		if(!ValidString(user) || !ValidString(password)) {
			model.addAttribute("user", user);
			model.addAttribute("password", password);
			model.addAttribute("errors", 
					"El usuario y contraseña no deben estar vacíos.");
			return "login";
		}
		
		if(hash != null) {
			if(encoder.matches(password, hash)) {
				session.setAttribute("user", user);
				model.addAttribute("user",user); // Línea añadida - Corrección de error
				System.out.println(USERS.toString());
				return "pagina1";
			}else {
				model.addAttribute("user", user);
				model.addAttribute("password", password);
				model.addAttribute("errors", "Contraseña incorrecta");
				return "login";
			}	
		}else {
			model.addAttribute("user", user);
			model.addAttribute("password", password);
			model.addAttribute("errors", 
					"El usuario no existe. Debe registrarse");
			return "login";
		}
		
	}
	
	@PostMapping("/registro")
	public String Register(
			@RequestParam(name="user", required=true) String user,
			@RequestParam(name="password", required=true) String password,
			HttpSession session,
			Model model
			) {
		
		if(!ValidString(user) || !ValidString(password)) {
			model.addAttribute("user", user);
			model.addAttribute("password", password);
			model.addAttribute("errors", 
					"Nombre de usuario o contraseña no válido");
			return "registro";
		}
		
		if(USERS.containsKey(user)) {
			model.addAttribute("user", user);
			model.addAttribute("password", password);
			model.addAttribute("errors", "El usuario ya existe");
			return "registro";
		}
		
		String hash = encoder.encode(password);
		USERS.put(user, hash);
		session.setAttribute("user", user);
		model.addAttribute("user", user);
		System.out.println(USERS.toString());
		
		return "pagina1";
	}
	
	private boolean logged(HttpSession session) {
		return session.getAttribute("user") != null;
	}
	
	private boolean ValidString(String string) {
		return string != null && !string.isBlank();
	}
	
}