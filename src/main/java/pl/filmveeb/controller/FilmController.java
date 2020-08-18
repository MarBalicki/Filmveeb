package pl.filmveeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        List<Film> allFilms = filmService.allFilms();
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

    @GetMapping("/addToFavorite/{id}")
    public RedirectView addToFavorite(@PathVariable("id") Long id, Authentication authentication) {
        User loggedUser = (User) authentication.getPrincipal();
        System.out.println(loggedUser);
        Set<Film> loggedUserFilmSet = loggedUser.getFilmSet();
        Film film = filmService.getById(id);
        System.out.println(film);
        loggedUserFilmSet.add(film);
        return new RedirectView("films");
    }


}
