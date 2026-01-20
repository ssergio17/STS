package entities;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	private Integer points;
	private LocalDateTime date;
	
	@Enumerated(EnumType.STRING)
	private Category category;

	// Getters
	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public Integer getPoints() {
		return points;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public Category getCategory() {
		return category;
	}

	// Setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
}
