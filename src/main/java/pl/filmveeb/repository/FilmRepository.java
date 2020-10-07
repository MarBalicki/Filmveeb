package pl.filmveeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.filmveeb.model.Film;
import pl.filmveeb.model.Genre;
import pl.filmveeb.model.Member;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {

    List<Film> findAllByGenre(Genre genre);
    boolean existsByTitleAndDirector(String title, Member director);
}
