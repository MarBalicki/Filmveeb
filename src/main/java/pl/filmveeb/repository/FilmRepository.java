package pl.filmveeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.filmveeb.model.Film;
import pl.filmveeb.model.Genre;

import java.util.List;
import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Long> {

    List<Film> findAllByGenre(Genre genre);
    Optional<Film> findByTitle(String title);
}
