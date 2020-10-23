package pl.filmveeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.filmveeb.model.ResetPassword;

import java.util.Optional;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {

    Optional<ResetPassword> findByToken(String token);
    Optional<ResetPassword> findByUser(Long id);
}
