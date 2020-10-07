package pl.filmveeb.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import pl.filmveeb.dto.FilmDto;
import pl.filmveeb.model.Genre;
import pl.filmveeb.model.Rating;
import pl.filmveeb.service.FilmService;
import pl.filmveeb.service.WeatherService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class FilmController {

    private final FilmService filmService;
    private final WeatherService weatherService;

    public FilmController(FilmService filmService, WeatherService weatherService) {
        this.filmService = filmService;
        this.weatherService = weatherService;
    }

    @GetMapping("/addFilm")
    public ModelAndView addFilm() {
        ModelAndView mav = new ModelAndView("addNewFilm");
        mav.addObject("newFilm", new FilmDto());
        return mav;
    }

    @PostMapping("/addFilm")
    public String addFilm(@Valid @ModelAttribute("newFilm") FilmDto filmDto, BindingResult bindingResult) {
//        bindingResult.rejectValue("productionYear", "toShort", "default error");
//        if (bindingResult.hasFieldErrors()) {
//            return "addNewFilm";
//        }
        filmService.addFilm(filmDto);
        return "redirect:/films";
    }

    @GetMapping("/films")
    public ModelAndView allFilms() {
        ModelAndView mav = new ModelAndView("/films");
        List<FilmDto> allFilmsDto = filmService.getAllFilms();
        mav.addObject("allFilmsDto", allFilmsDto);
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && SecurityContextHolder.getContext().getAuthentication() != null
                && !(SecurityContextHolder.getContext().getAuthentication()
                instanceof AnonymousAuthenticationToken)) {
            String cityTemperature = weatherService.getCityWeather();
            mav.addObject("cityTemperature", cityTemperature);
        }
        return mav;
    }

    @GetMapping("/editFilm/{id}")
    public ModelAndView editFilm(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("editFilm");
        FilmDto filmDto = filmService.getFilmDtoById(id);
        mav.addObject(filmDto);
        return mav;
    }

    @PostMapping("/updateFilm")
    public RedirectView updateFilm(@ModelAttribute FilmDto filmDto) {
        filmService.updateFilm(filmDto);
        return new RedirectView("/films");
    }

    @PostMapping("/deleteFilm/{id}")
    public RedirectView deleteFilm(@PathVariable("id") Long id) {
        filmService.deleteFilmById(id);
        return new RedirectView("/films");
    }

    @GetMapping("/allFilms/{genre}")
    public ModelAndView pickFilmsByGenre(@PathVariable("genre") Genre genre) {
        ModelAndView mav = new ModelAndView("/allFilms");
        List<FilmDto> allFilmsByGenreDto = filmService.getAllFilmsByGenre(genre);
        mav.addObject("allFilmsByGenreDto", allFilmsByGenreDto);
        mav.addObject("genre", genre);
        return mav;
    }

    @GetMapping("/filmDetails/{id}")
    public ModelAndView filmDetails(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("/filmDetails");
        FilmDto filmDto = filmService.getFilmDtoById(id);
        Rating rating = new Rating();
        mav.addObject("rating", rating);
        mav.addObject(filmDto);
        return mav;
    }


}
