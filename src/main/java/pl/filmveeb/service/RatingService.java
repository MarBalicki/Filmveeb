package pl.filmveeb.service;

import org.springframework.stereotype.Service;
import pl.filmveeb.model.Rating;
import pl.filmveeb.repository.RatingRepository;

import java.time.LocalDate;

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

    public void save(Rating rating) {
        if (ratingRepository.existsRatingByUserAndFilm(rating.getUser(), rating.getFilm())) {
            //todo if exists make update
            System.out.println("This User rated this film before!");
        } else {
            ratingRepository.save(rating);
        }
    }
    public void rateFilm(Long id, String stars) {
        Rating newRating = new Rating();
        newRating.setStars(Integer.parseInt(stars));
        newRating.setDate(LocalDate.now());
        newRating.setUser(userService.getLoggedUser());
        newRating.setFilm(filmService.getFilmById(id));
        save(newRating);
    }
}
