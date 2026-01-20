package repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import entities.User;

public interface AnswerRepository extends JpaRepository <User, Long>{
	
}
