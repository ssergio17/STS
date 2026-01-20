package entities;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL)
	private List<Answer> answers;
	
	public User() {}
	public User(String name) {
		this.name = name;
	}
	
	// Getters
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	
	// Setters
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
}
