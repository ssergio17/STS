package com.example.demo;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
	private ProductRepository ProductRepository;
	private LogRepository logRepository;
	
	public CartController(ProductRepository repository, LogRepository logRepository) {
		this.ProductRepository = repository;
		this.logRepository = logRepository;
	}
	
	@GetMapping("/")
	public String Root() {
		return "bienvenida";
	}
	
	@PostMapping("/")
	public String SaveName(
			@RequestParam(name="user") String user,
			HttpSession session
			) {
		session.setAttribute("user", user);
		return "redirect:/carrito";
	}
	
	@GetMapping("/carrito")
	public String ShowCart(
			HttpSession session,
			Model model
			) {
		Map<String, Integer> cart = GetCart(session);
		
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("products", ProductRepository.findAll());
		model.addAttribute("cart", cart);
		model.addAttribute("total", CalculateTotal(cart));
		return "carrito";
	}
	
	@PostMapping("/carrito")
	public String UpdateCart(
			@RequestParam Map<String, String> products,
			HttpSession session,
			Model model
			) {
		Map<String, Integer> cart = GetCart(session);
		
		for(Product product : ProductRepository.findAll()) {
			String name = product.getName();
			String value = products.get(name);
			
			// Paso 1
			if(value == null || value.isBlank()) {
				String error = "Valor inválido para: " + product;
				return CartError(model, session, cart, error);
			}
			
			// Paso 2
			int order;
			try {
				order = Integer.parseInt(value.trim());
			}catch(NumberFormatException e) {
				String error = "Valor de '" + product + "' no válido.";
				return CartError(model,session,cart, error);
			}
			
			// Paso 3
			if(order < 0) order = 0;
			int realStock = product.getStock();
			if(order > realStock) order = realStock;
			cart.put(name, order);
		}
		
		session.setAttribute("cart", cart);
		return "redirect:/carrito";
	}
	
	@PostMapping("/confirmar")
	public String ConfirmPurchase(
			HttpSession session,
			Model model
			) {
		Map<String, Integer> cart = GetCart(session);
		String error = "";
		
		for(String product : cart.keySet()) {
			Integer quantity = cart.get(product);
			
			// Paso 1
			if(quantity == null || quantity < 0) {
				error  = "Cantidad no válida para: " + product;
				return CartError(model, session, cart, error);
			}
			
			// Paso 2
			List<Product> result = ProductRepository.findByName(product);
			if(result.isEmpty()) {
				error = "Producto no encontrado en BD: " + product;
				return CartError(model, session, cart, error);
			}
			
			Product p = result.get(0);
			int realStock = p.getStock();
			
			if(quantity > realStock) {
				error = "No hay suficiente stock para: " + product;
				return CartError(model, session, cart, error);
			}
		}
		
		for(String product : cart.keySet()) {
			Integer quantity = cart.get(product);
			List<Product> result = ProductRepository.findByName(product);
			
			Product p = result.get(0);
			int newStock = p.getStock() - quantity;
			
			p.setStock(newStock);
			ProductRepository.save(p);
		}
		
		SaveLog(cart, session);
		
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("purchased", cart);
		
		session.setAttribute("cart", new HashMap<>());
		return "confirmacion";
	}
	
	@GetMapping("/admin")
	public String Admin(Model model) {
		Iterable<Product> products = ProductRepository.findAll();
		model.addAttribute("products", products);
		return "admin";
	}
	
	@PostMapping("/admin/update")
	public String UpdateStock(
			@RequestParam Map<String, String> products
			) {
		for(Product p : ProductRepository.findAll()) {
			String name = p.getName();
			String value = products.get(name);
			
			if(value != null && !value.trim().isEmpty()) {
				try {
					int newStock = Integer.parseInt(value.trim());
					if(newStock >= 0) {
						p.setStock(newStock);
						ProductRepository.save(p);
					}
				}catch(NumberFormatException e) {
					
				}
			}
		}
		return "redirect:/admin";
	}
	
	// Métodos auxiliares
	private Map<String, Integer> GetCart(HttpSession session) {
		Map<String, Integer> cart = 
				(Map<String, Integer>) session.getAttribute("cart");
		
		if(cart == null) {
			cart = new HashMap<>();
			session.setAttribute("cart", cart);
		}
		return cart;
	}
	
	private double CalculateTotal(Map<String, Integer> cart) {
		double total = 0;
		
		for(String product : cart.keySet()) {
			Integer quantity = cart.get(product);
			
			if(quantity != null && quantity > 0) {
				List<Product> result = ProductRepository.findByName(product);
				if(!result.isEmpty()) {
					Product p = result.get(0);
					double price = p.getPrice();
					total += price * quantity;
				}
			}
		}
		return total;
	}
	
	private String CartError(
			Model model,
			HttpSession session,
			Map<String, Integer> cart,
			String message
			) {
		model.addAttribute("errors", message);
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("products", ProductRepository.findAll());
		model.addAttribute("cart", cart);
		model.addAttribute("total", CalculateTotal(cart));
		return "carrito";
	}
	
	private void SaveLog(
			Map<String, Integer> cart,
			HttpSession session
			) {
		String user = (String) session.getAttribute("user");
		String date = LocalDateTime.now().toString();
		String products = "";
		
		for(String product : cart.keySet()) {
			products += product + "=" + cart.get(product) + ", ";
		}
		
		if(products.endsWith(", ")) {
			products = products.substring(0, products.length()-2);
		}
		
		Log log = new Log(user, date, products);
		logRepository.save(log);
	}
	
}
