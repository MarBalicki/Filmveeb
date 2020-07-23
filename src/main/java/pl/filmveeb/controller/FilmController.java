package pl.filmveeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import pl.filmveeb.model.Film;
import pl.filmveeb.service.FilmService;

import java.util.List;

@Controller
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping("/films")
    public String allFilms(Model model) {
        List<Film> allFilms = filmService.listAll();
        model.addAttribute("allFilms", allFilms);
        return "films";
    }

    @GetMapping("/addFilm")
    public ModelAndView addFilm() {
        return new ModelAndView("addNewFilm", "filmToInsert", new Film());
    }

    @PostMapping("/addFilm")
    public RedirectView addFilm(@ModelAttribute Film film) {
        filmService.save(film);
        return new RedirectView("/films");
    }

    @RequestMapping("/editFilm/{id}")
    public ModelAndView editFilm(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("editFilm");
        Film film = filmService.get(id);
        mav.addObject(film);
        return mav;
    }

    @RequestMapping("/deleteFilm/{id}")
    public RedirectView deleteFilm(@PathVariable("id") Long id) {
        filmService.delete(id);
        return new RedirectView("/films");
    }


}
