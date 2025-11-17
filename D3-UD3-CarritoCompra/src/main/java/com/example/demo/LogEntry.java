package com.example.demo;
import java.util.Map;

public class LogEntry {
	private String user;
	private String date;
	private Map<String, Integer> products;
	
	public LogEntry() {}
	public LogEntry(String user, String date, Map<String, Integer> products) {
		this.user = user;
		this.date = date;
		this.products = products;
	}
	
	// Getters
	public String getUser() {
		return user;
	}
	
	public String getDate() {
		return date;
	}
	
	public Map<String, Integer> getProducts() {
		return products;
	}
	
	// Setters
	public void setUser(String user) {
		this.user = user;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void setProducts(Map<String, Integer> products) {
		this.products = products;
	}
	
	
}
