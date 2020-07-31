package pl.filmveeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.filmveeb.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
