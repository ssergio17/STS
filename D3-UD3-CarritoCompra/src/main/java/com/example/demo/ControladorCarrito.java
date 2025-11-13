package com.example.demo;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class ControladorCarrito {
	private final RepositorioStock stock;
	
	public ControladorCarrito() {
		stock = new RepositorioStock();
	}
	
	@GetMapping("/")
	public String welcome() {
		return "bienvenida";
	}
	
	@GetMapping("/carrito")
	public String cart(Model model, HttpSession session) {
		
		if(session.getAttribute("user") == null) {
			return "bienvenida";
		}
		
		Map<String, Integer> cart = getCart(session);
		
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("products", stock.getAll());
		model.addAttribute("cart", cart);
		return "carrito";
	}
	
	@GetMapping("/admin")
	public String admin(Model model) {
		model.addAttribute("products", stock.getAll());
		return "admin";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam(name="user", required=true) String user, HttpSession session) {
		session.setAttribute("user", user);
		
		session.setAttribute("cart", new HashMap<String, Integer>());
		return "carrito";
	}
	
	@PostMapping("/actualizar")
	public String updateCart(
			@RequestParam Map<String, String> prods,
			HttpSession session,
			Model model
			) {
		
		Map<String, Integer> cart = getCart(session);
		
		prods.forEach((key, value) -> {
			if(key.startsWith("prod_")) {
				String product = key.substring(5);
				int required = Integer.parseInt(value);
				int available = stock.getStock(product);
				
				if(required > available) required = available;
				if(required < 0) required = 0;
				
				cart.put(product, required);
			}
			
		});
		
		session.setAttribute("cart", cart);
		model.addAttribute("products", stock.getAll());
		model.addAttribute("cart", cart);
		
		return "carrito";
	}
	
	@PostMapping("/finalizar")
	public String completePurchase(HttpSession session, Model model) {
		Map<String, Integer> cart = getCart(session);
		
		for(var e : cart.entrySet()) {
			if(e.getValue() > stock.getStock(e.getKey())) {
				model.addAttribute("error", "No hay stock suficiente para: " + e.getKey());
				model.addAttribute("products", stock.getAll());
				model.addAttribute("cart", cart);
				return "carrito";
			}
		}
		
		for(var e : cart.entrySet()) {
			int nw = stock.getStock(e.getKey()) - e.getValue();
			stock.modifyStock(e.getKey(), nw);
		}
		
		writeLog((String) session.getAttribute("user"), cart);
		
		cart.clear();
		session.setAttribute("cart", cart);
		
		return "confirmacion";
	}
	
	@PostMapping("/admin/actualizar")
	public String updateStock(
			@RequestParam(name="prod", required=true) String product,
			@RequestParam(name="quantity", required=true) Integer quantity
			) {
		stock.modifyStock(product, quantity);
		return "admin";
	}
	
	private Map<String, Integer> getCart(HttpSession session) {
		Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");
		
		if(cart == null) {
			cart = new HashMap<>();
			session.setAttribute("cart", cart);
		}
		return cart;
	}
	
	private void writeLog(String user, Map<String, Integer> cart) {
		try(FileWriter fw = new FileWriter("compras.log", true)) {
			fw.write("Usuario: " + user + " - " + cart.toString() + "\n");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
