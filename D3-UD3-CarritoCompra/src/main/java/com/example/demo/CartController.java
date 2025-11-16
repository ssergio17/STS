package com.example.demo;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {

	private final Stock stock;
	private static final String LOG_PATH = "logs.json";
	
	public CartController() {
		stock = new Stock();
	}
	
	@GetMapping("/")
	public String Root() {
		return "bienvenida";
	}
	
	@PostMapping("/")
	public String SaveName(
			@RequestParam(name="user", required=true) String user,
			HttpSession session,
			Model model
			) {
		session.setAttribute("user", user);
		model.addAttribute("user", user);
		return "redirect:/carrito";
	}
	
	@GetMapping("/carrito")
	public String ShowCart(
			HttpSession session, Model model
			) {
		Map<String, Integer> cart = GetCart(session);
		
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("products", stock.getAll());
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
		String errors = "";
		Map<String, Integer> cart = GetCart(session);
		
		if(HasInvalidValues(cart)) {
			errors = "Valores no válidos";
			model.addAttribute("errors", errors);
			model.addAttribute("user", session.getAttribute("user"));
			model.addAttribute("products", stock.getAll());
			model.addAttribute("cart", cart);
			model.addAttribute("total", CalculateTotal(cart));
			return "carrito";
		}
		
		for(String product : stock.getAll().keySet()) {
			String value = products.get(product);
			
			if(value != null && !value.trim().isEmpty()) {
				int order = Integer.parseInt(value);
				Product p = stock.getProduct(product);
				int realStock = p.getStock();
				
				if(order < 0) order = 0;
				if(order > realStock) order = realStock;
				
				cart.put(product, order);
			}
		}
		
		session.setAttribute("cart", cart);
		return "redirect:/carrito";
	}
	
	@PostMapping("/confirmar")
	public String ConfirmPurchase(
			HttpSession session,
			Model model
			) {
		String errors  = "";
		Map<String, Integer> cart = GetCart(session);
		
		if(HasInvalidValues(cart)) {
			errors = "Valores no válidos";
			model.addAttribute("errors", errors);
			model.addAttribute("user", session.getAttribute("user"));
			model.addAttribute("products", stock.getAll());
			model.addAttribute("cart", cart);
			model.addAttribute("total", CalculateTotal(cart));
			return "carrito";
		}
		
		for(String product : cart.keySet()) {
			int order = cart.get(product);
			int realStock = stock.getProduct(product).getStock();
			
			if(order > realStock) {
				model.addAttribute("errors", "No hay stock suficiente");
				model.addAttribute("user", session.getAttribute("user"));
				model.addAttribute("products", stock.getAll());
				model.addAttribute("cart", cart);
				model.addAttribute("total", CalculateTotal(cart));
				return "carrito";
			}
		}
		
		for(String product : cart.keySet()) {
			Product p = stock.getProduct(product);
			int newStock = p.getStock() - cart.get(product);
			p.setStock(newStock);
		}
		stock.save();
		
		SavePurchaseJSON(session, cart);
		
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("purchased", cart);
		
		session.setAttribute("cart", new HashMap<>());
		
		return "confirmacion";
	}
	
	@GetMapping("/admin")
	public String admin(Model model) {
		model.addAttribute("products", stock.getAll());
		return "admin";
	}
	
	@PostMapping("/admin/update")
	public String UpdateStock(
			@RequestParam Map<String, String> products
			) {
		for(String product : stock.getAll().keySet()) {
			String value = products.get(product);
			
			if(value != null) {
				int nw = Integer.parseInt(value);
				if(nw < 0) nw = 0;
				
				stock.getProduct(product).setStock(nw);
			}
		}
		stock.save();
		return "redirect:/admin";
	}
	
	private Map<String, Integer> GetCart(HttpSession session) {
		Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");
		
		if(cart == null) {
			cart = new HashMap<>();
			session.setAttribute("cart", cart);
		}
		return cart;
	}
	
	
	// Métodos auxiliares
	
	private double CalculateTotal(Map<String, Integer> cart) {
		double total = 0;
		
		for(String product  : cart.keySet()) {
			Product p = stock.getProduct(product);
			int quantity = cart.get(product);
			total += p.getPrice() * quantity;
		}
		return total;
	}
	
	private boolean HasInvalidValues(Map<String, Integer> map) {
		for (String key : map.keySet()) {
	        Integer value = map.get(key);
	        return value == null || value == 0 || value < 0;
	    }
	    return false;
	}

	
	private List<LogEntry> LoadLog() {
		ObjectMapper mapper = new ObjectMapper();
		List<LogEntry> logs = null;
		
		try {
			File file = new File(LOG_PATH);
			logs = mapper.readValue(file, 
					new TypeReference<List<LogEntry>>() {});
		} catch(IOException e) {
			System.out.println(e.getMessage());
			logs = new ArrayList<>();
		}
		return logs;
	}
	
	private void SaveLog(List<LogEntry> logs) {

	    ObjectMapper mapper = new ObjectMapper();
	    File fichero = new File(LOG_PATH);

	    try {
	        mapper
	            .writerWithDefaultPrettyPrinter()
	            .writeValue(fichero, logs);
	    } catch (IOException e) {
	        System.out.println("Error guardando el archivo de log: " + e.getMessage());
	    }
	}
	
	private void SavePurchaseJSON(
			HttpSession session,
			Map<String, Integer> cart
			) {
		List<LogEntry> logs = LoadLog();
		LogEntry entry = new LogEntry(
				(String) session.getAttribute("user"),
				LocalDateTime.now().toString(),
				new HashMap<>(cart)
				);
		
		logs.add(entry);
		SaveLog(logs);
	}
}
