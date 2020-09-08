package pl.filmveeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.filmveeb.model.Rate;

public interface RateRepository extends JpaRepository<Rate, Long> {
}
