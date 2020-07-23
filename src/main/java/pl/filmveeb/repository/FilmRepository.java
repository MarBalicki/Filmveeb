package pl.filmveeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.filmveeb.model.Film;

public interface FilmRepository extends JpaRepository<Film, Long> {
}
