package repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import entities.Answer;

public interface UserRepository extends JpaRepository<Answer, Long>{

}
