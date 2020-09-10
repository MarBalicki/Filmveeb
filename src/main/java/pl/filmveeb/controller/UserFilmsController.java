package pl.filmveeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import pl.filmveeb.model.Film;
import pl.filmveeb.model.Genre;
import pl.filmveeb.model.User;
import pl.filmveeb.repository.RateRepository;
import pl.filmveeb.service.UserService;

import java.util.Set;

@Controller
public class UserFilmsController {

    private final UserService userService;
    private final RateRepository rateRepository;

    public UserFilmsController(UserService userService, RateRepository rateRepository) {
        this.userService = userService;
        this.rateRepository = rateRepository;
    }

    @GetMapping("/userFilms")
    public String allMyFilms(Model model) {
        try {
            User currentUser = userService.getLoggedUser();
            Set<Film> userFilms = currentUser.getFilms();
            model.addAttribute("userFilms", userFilms);
        } catch (RuntimeException ex) {
            return "redirect:/login";
        }
        return "userFilms";
    }

    @GetMapping("/userFilms/{genre}")
    public ModelAndView pickMyFilmsByGenre(@PathVariable("genre") Genre genre) {
        ModelAndView mav = new ModelAndView("/userFilms");
        User user = userService.getLoggedUser();
        Set<Film> userFilms = userService.getAllUserFilmsByGenre(user, genre);
        mav.addObject("userFilms", userFilms);
        return mav;
    }

    //todo remove filmService
    @PostMapping("/addToFavorite/{id}")
    public RedirectView addFilmToFavorite(@PathVariable("id") Long id) {
        User currentUser = userService.getLoggedUser();
        currentUser.getFilms().add(userService.takeFilm(id));
        userService.saveUser(currentUser);
        return new RedirectView("/userFilms");
    }

    @PostMapping("/removeFilm/{id}")
    public RedirectView removeFromFavorite(@PathVariable("id") Long id) {
        User currentUser = userService.getLoggedUser();
        currentUser.getFilms().remove(userService.takeFilm(id));
        userService.saveUser(currentUser);
        return new RedirectView("/userFilms");
    }


}
