package com.example.demo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Stock {
	protected Map<String, Product> stock;
	protected final static String FILE_PATH = "stock.json";
	
	public Stock() {
		super();
		stock = new HashMap<>();
		load();
	}
	
	public void load() {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			this.stock = mapper.readValue(new File(FILE_PATH),
					new TypeReference<Map<String, Product>>() {});
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public boolean save() {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			mapper.writerWithDefaultPrettyPrinter().
			writeValue(new File(FILE_PATH), stock);
		} catch(FileNotFoundException e) {
			System.out.println("El fichero no existe");
			System.out.println("Creando fichero vacío...");
			stock = new HashMap<>();
		} catch(IOException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	public Product getProduct(String product) {
		return stock.get(product);
	}
	
	public double getPrice(String product) {
		return stock.get(product).getPrice();
	}
	
	public Map<String, Product> getAll() {
		return stock;
	}
	
	public int getStock(String product) {
		return stock.get(product).getStock();
	}
	
	public void addProduct(Product product) {
		stock.put(product.getName(), product);
		save();
	}
	
	public void removeProduct(String product) {
		stock.remove(product);
		save();
	}
	
	public void modifyProductStock(String product, int quantity) {
		stock.get(product).setStock(quantity);;
		save();
	}
	
}