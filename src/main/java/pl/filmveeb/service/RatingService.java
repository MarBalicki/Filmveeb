package pl.filmveeb.service;

import org.springframework.stereotype.Service;
import pl.filmveeb.dto.RatingDto;
import pl.filmveeb.model.Rating;
import pl.filmveeb.model.User;
import pl.filmveeb.repository.RatingRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

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

    public void rateFilm(Long filmId, Rating newRating) {
        if (!filmService.isInFavorites(filmId)) {
            filmService.addToFavorite(filmId);
        }
        User loggedUser = userService.getLoggedUser();
        Optional<Rating> ratingOptional = ratingRepository.findByFilm_IdAndUser_Id(filmId, loggedUser.getId());
        if (ratingOptional.isPresent()) {
            updateRating(ratingOptional.get(), newRating);
        } else {
            saveNewRating(filmId, newRating, loggedUser);
        }
    }

    private void updateRating(Rating oldRating, Rating newRating) {
        Rating currentRating = ratingRepository.getOne(oldRating.getId());
        currentRating.setRatingValue(newRating.getRatingValue());
        currentRating.setDate(oldRating.getDate());
        currentRating.setUser(oldRating.getUser());
        currentRating.setFilm(oldRating.getFilm());
        ratingRepository.save(currentRating);
    }

    private void saveNewRating(Long filmId, Rating newRating, User loggedUser) {
        Rating rating = new Rating();
        rating.setRatingValue(newRating.getRatingValue());
        rating.setDate(LocalDate.now());
        rating.setUser(loggedUser);
        rating.setFilm(filmService.getFilmById(filmId));
        ratingRepository.save(rating);
    }

    public Optional<RatingDto> getRatingByFilmIdAndLoggedUser(Long id) {

        User loggedUser = userService.getLoggedUser();
        return ratingRepository.findByFilm_IdAndUser_Id(id, loggedUser.getId())
                .map(RatingDto::apply);
    }

    public void deleteAllFilmRatings(Set<Rating> rates) {
        ratingRepository.deleteAll(rates);
    }
}
