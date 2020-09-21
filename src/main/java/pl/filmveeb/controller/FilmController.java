package pl.filmveeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import pl.filmveeb.dto.FilmDto;
import pl.filmveeb.model.Genre;
import pl.filmveeb.service.FilmService;

import java.util.List;

@Controller
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/addFilm")
    public ModelAndView addFilm() {
        ModelAndView mav = new ModelAndView("addNewFilm");
        mav.addObject("newFilm", new FilmDto());
        return mav;
    }

    @PostMapping("/addFilm")
    public RedirectView addFilm(@ModelAttribute FilmDto filmDto) {
        filmService.addFilm(filmDto);
        return new RedirectView("/films");
    }

    @GetMapping("/films")
    public ModelAndView allFilms() {
        ModelAndView mav = new ModelAndView("/films");
        List<FilmDto> allFilmsDto = filmService.getAllFilms();
        mav.addObject("allFilmsDto", allFilmsDto);
        return mav;
    }

    @GetMapping("/editFilm/{id}")
    public ModelAndView editFilm(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("editFilm");
        FilmDto filmDto = filmService.getFilmById(id);
        mav.addObject(filmDto);
//        Set<User> users = filmDto.getUsers();
//        mav.addObject("users", users);
        return mav;
    }

    @PostMapping("/updateFilm/{id}")
    public RedirectView updateFilm(@PathVariable("id") Long id, @ModelAttribute FilmDto filmDto) {
        filmService.updateFilm(id, filmDto);
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


}
