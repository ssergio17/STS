package entities;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
	private List<CartLine> lines = new ArrayList<>();
	
	public Cart() {}
	public Cart(User user) {
		this.user = user;
	}
	
	// Getters
	public Long getId() {
		return id;
	}
	public User getUser() {
		return user;
	}
	public List<CartLine> getLines() {
		return lines;
	}
	
	// Setters
	public void setId(Long id) {
		this.id = id;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setLines(List<CartLine> items) {
		this.lines = items;
	}
	
	public void addLine(CartLine cartLine) {
		lines.add(cartLine);
		cartLine.setCart(this);
	}
	
	public void removeLine(CartLine cartLine) {
		lines.remove(cartLine);
		cartLine.setCart(null);
	}
	
}
