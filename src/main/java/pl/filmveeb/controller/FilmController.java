package pl.filmveeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import pl.filmveeb.model.Film;
import pl.filmveeb.model.Genre;
import pl.filmveeb.service.FilmService;

import java.util.List;

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
        filmService.save(film);
        return new RedirectView("/films");
    }

    @GetMapping("/editFilm/{id}")
    public ModelAndView editFilm(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("editFilm");
        Film film = filmService.getById(id);
        mav.addObject(film);
        return mav;
    }

    @GetMapping("/deleteFilm/{id}")
    public RedirectView deleteFilm(@PathVariable("id") Long id) {
        filmService.delete(id);
        return new RedirectView("/films");
    }

    @GetMapping("/allFilms/{genre}")
    public String pickFilmsByGenre(@PathVariable("genre")Genre genre, Model model) {
        List<Film> allFilmsByGenre = filmService.getAllFilmsByGenre(genre);
        model.addAttribute("allFilmsByGenre", allFilmsByGenre);
        return"allFilms";
    }




}
