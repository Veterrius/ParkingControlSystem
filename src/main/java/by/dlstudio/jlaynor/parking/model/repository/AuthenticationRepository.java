package by.dlstudio.jlaynor.parking.model.repository;

import by.dlstudio.jlaynor.parking.model.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepository extends JpaRepository<User, Integer> {
    User getUserByEmailAndPassword(String email, String password);
}
