package entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private double price; 
	private Integer stock;
	
	private Product(String name, Integer stock) {
		this.name = name;
		this.stock = stock;
	}

	// Getters
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public double getPrice() {
		return price;
	}

	public Integer getStock() {
		return stock;
	}

	// Setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
}
