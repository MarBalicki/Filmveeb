package pl.filmveeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.filmveeb.model.Film;
import pl.filmveeb.service.FilmService;

@Controller
public class IndexController {

    @Autowired
    private FilmService filmService;

    @RequestMapping("/index")
    public String index() {
//        filmService.save(new Film(1l, "Django", "2012", "Quentin Tarantino", "Western", "Great movie!"));
        return "index";
    }
}
