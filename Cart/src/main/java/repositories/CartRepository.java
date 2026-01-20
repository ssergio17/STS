package repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import entities.Cart;
import entities.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart findByUser(User user);
}
