package pl.filmveeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import pl.filmveeb.model.Film;
import pl.filmveeb.model.Genre;
import pl.filmveeb.model.User;
import pl.filmveeb.service.FilmService;

import java.util.List;
import java.util.Set;

@Controller
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public String allFilms(Model model) {
        List<Film> allFilms = filmService.getAllFilms();
        model.addAttribute("allFilms", allFilms);
        return "films";
    }

    @GetMapping("/addFilm")
    public ModelAndView addFilm() {
        return new ModelAndView("addNewFilm", "newFilm", new Film());
    }

    @PostMapping("/addFilm")
    public RedirectView addFilm(@ModelAttribute Film film) {
        filmService.addFilm(film);
        return new RedirectView("/films");
    }

    @GetMapping("/editFilm/{id}")
    public ModelAndView editFilm(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("editFilm");
        Film film = filmService.getFilmById(id);
        mav.addObject(film);
        Set<User> users = film.getUsers();
        mav.addObject("users", users);
        return mav;
    }

    @PostMapping("/updateFilm/{id}")
    public RedirectView updateFilm(@PathVariable("id") Long id, @ModelAttribute Film filmModel) {
        filmService.updateFilm(id, filmModel);
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
        List<Film> allFilmsByGenre = filmService.getAllFilmsByGenre(genre);
        mav.addObject("allFilmsByGenre", allFilmsByGenre);
        mav.addObject("genre", genre);
        return mav;
    }


}
