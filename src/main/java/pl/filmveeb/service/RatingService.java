package pl.filmveeb.service;

import org.springframework.stereotype.Service;
import pl.filmveeb.dto.RatingDto;
import pl.filmveeb.model.Rating;
import pl.filmveeb.model.User;
import pl.filmveeb.repository.RatingRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final FilmService filmService;
    private final UserService userService;

    public RatingService(RatingRepository ratingRepository, FilmService filmService, UserService userService) {
        this.ratingRepository = ratingRepository;
        this.filmService = filmService;
        this.userService = userService;
    }

    public void rateFilm(Long filmId, String stars) {
        if (!filmService.isInFavorites(filmId)) {
            filmService.addToFavorite(filmId);
        }
        User loggedUser = userService.getLoggedUser();
        Optional<Rating> ratingOptional = ratingRepository.findByFilm_IdAndUser_Id(filmId, loggedUser.getId());
        if (ratingOptional.isPresent()) {
            updateRating(ratingOptional.get(), stars);
        } else {
            saveNewFilm(filmId, stars, loggedUser);
        }
    }

    private void updateRating(Rating rating, String stars) {
        Rating currentRating = ratingRepository.getOne(rating.getId());
        currentRating.setStars(Integer.parseInt(stars));
        currentRating.setDate(rating.getDate());
        currentRating.setUser(rating.getUser());
        currentRating.setFilm(rating.getFilm());
        ratingRepository.save(currentRating);
    }

    private void saveNewFilm(Long filmId, String stars, User loggedUser) {
        Rating newRating = new Rating();
        newRating.setStars(Integer.parseInt(stars));
        newRating.setDate(LocalDate.now());
        newRating.setUser(loggedUser);
        newRating.setFilm(filmService.getFilmById(filmId));
        ratingRepository.save(newRating);
    }

    public Optional<RatingDto> getRatingByFilmIdAndLoggedUser(Long id) {
        User loggedUser = userService.getLoggedUser();
        return ratingRepository.findByFilm_IdAndUser_Id(id, loggedUser.getId())
                .map(RatingDto::apply);
    }
}
