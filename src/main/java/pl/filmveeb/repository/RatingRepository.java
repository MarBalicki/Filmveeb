package pl.filmveeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.filmveeb.model.Film;
import pl.filmveeb.model.Rating;
import pl.filmveeb.model.User;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    boolean existsRatingByUserAndFilm(User user, Film film);
    Optional<Rating> findByFilm_IdAndUser_Id(Long filmId, Long userId);
}
