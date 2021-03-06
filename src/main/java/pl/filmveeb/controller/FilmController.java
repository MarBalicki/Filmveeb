package pl.filmveeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import pl.filmveeb.dto.FilmDto;
import pl.filmveeb.dto.RatingDto;
import pl.filmveeb.model.Genre;
import pl.filmveeb.model.Rating;
import pl.filmveeb.service.FilmService;
import pl.filmveeb.service.RatingService;
import pl.filmveeb.service.UserService;
import pl.filmveeb.service.WeatherService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class FilmController {

    private final FilmService filmService;
    private final WeatherService weatherService;
    private final RatingService ratingService;
    private final UserService userService;

    public FilmController(FilmService filmService, WeatherService weatherService,
                          RatingService ratingService, UserService userService) {
        this.filmService = filmService;
        this.weatherService = weatherService;
        this.ratingService = ratingService;
        this.userService = userService;
    }

    @GetMapping("/addFilm")
    public ModelAndView addFilm() {
        ModelAndView mav = new ModelAndView("addNewFilm");
        mav.addObject("newFilm", new FilmDto());
        personalizedWeather(mav);
        return mav;
    }

    @PostMapping("/addFilm")
    public String addFilm(@Valid @ModelAttribute("newFilm") FilmDto filmDto,
                          BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("newFilm", filmDto);
            return "addNewFilm";
        }
        filmService.addFilm(filmDto);
        return "redirect:/films";
    }

    @GetMapping("/films")
    public ModelAndView allFilms() {
        ModelAndView mav = new ModelAndView("/films");
        List<FilmDto> allFilmsDto = filmService.getAllFilms();
        mav.addObject("allFilmsDto", allFilmsDto);
        personalizedWeather(mav);
        return mav;
    }

    @GetMapping("/films/{genre}")
    public ModelAndView pickFilmsByGenre(@PathVariable("genre") Genre genre) {
        ModelAndView mav = new ModelAndView("/films");
        mav.addObject("genre", "w kategorii " + genre);
        List<FilmDto> allFilmsDto = filmService.getAllFilmsByGenre(genre);
        mav.addObject("allFilmsDto", allFilmsDto);
        personalizedWeather(mav);
        return mav;
    }

    @GetMapping("/editFilm/{id}")
    public ModelAndView editFilm(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("editFilm");
        FilmDto filmDto = filmService.getFilmDtoById(id);
        mav.addObject(filmDto);
        return mav;
    }

    @PostMapping("/editFilm")
    public String updateFilm(@Valid @ModelAttribute FilmDto filmDto,
                                   BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("filmDto", filmDto);
            return "/editFilm";
        }
        filmService.updateFilm(filmDto);
        return "redirect:/films";
    }

    @PostMapping("/deleteFilm/{id}")
    public RedirectView deleteFilm(@PathVariable("id") Long id) {
        filmService.deleteFilmById(id);
        return new RedirectView("/films");
    }

    @GetMapping("/filmDetails/{id}")
    public ModelAndView filmDetails(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("/filmDetails");
        FilmDto filmDto = filmService.getFilmDtoById(id);
        mav.addObject(filmDto);
        Rating rating = new Rating();
        mav.addObject("rating", rating);
        if (userService.userIsLogged()) {
            Optional<RatingDto> currentRatingDtoOptional = ratingService.getRatingByFilmIdAndLoggedUser(filmDto.getId());
            currentRatingDtoOptional.ifPresent(ratingDto -> mav.addObject("ratingDto", ratingDto));
        }
        personalizedWeather(mav);
        return mav;
    }

    private void personalizedWeather(ModelAndView mav) {
        if (userService.userIsLogged()) {
            mav.addObject("cityTemperature", weatherService.getCityWeather());
        }
    }


}
