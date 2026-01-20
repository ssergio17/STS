package repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import entities.CartLine;

public interface CartLineRepository extends JpaRepository<CartLine, Long>{
}
