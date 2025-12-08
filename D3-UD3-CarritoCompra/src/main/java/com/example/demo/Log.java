package com.example.demo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Log {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String user, date, cart;
	
	public Log() {}
	public Log(String user, String date, String cart) {
		this.user = user;
		this.date = date;
		this.cart = cart;
	}
	
	// Getters
	public long getId() {
		return id;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getCart() {
		return cart;
	}
	
	// Setters
	public void setUser(String user) {
		this.user = user;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void setCart(String cart) {
		this.cart = cart;
	}
}
