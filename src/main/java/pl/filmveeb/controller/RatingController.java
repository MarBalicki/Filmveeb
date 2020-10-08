package pl.filmveeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.filmveeb.service.RatingService;

@Controller
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/rate/{id}")
    public String rateFilm(@PathVariable("id") Long filmId, @RequestParam("rating") String stars) {
        ratingService.rateFilm(filmId, stars);
        return "redirect:/films";
    }

}
