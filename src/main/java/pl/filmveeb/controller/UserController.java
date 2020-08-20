package pl.filmveeb.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import pl.filmveeb.model.Film;
import pl.filmveeb.model.Genre;
import pl.filmveeb.model.User;
import pl.filmveeb.service.FilmService;
import pl.filmveeb.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;
    private final FilmService filmService;

    public UserController(UserService userService, FilmService filmService) {
        this.userService = userService;
        this.filmService = filmService;
    }

    @GetMapping("/registerUser")
    public ModelAndView registerUserForm() {
        return new ModelAndView("registerUser", "newUser", new User());
    }

//    @GetMapping
//    public void getAllUsers(Model model) {
//        List<User> allUsers = userService.getAllUsers();
//        model.addAttribute("allUsers", allUsers);
//    }

    @PostMapping("/addUser")
    public RedirectView addUser(@ModelAttribute User user) {
        userService.addUser(user);
        return new RedirectView("index");
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/editUser/{emial}")
    public String editUser(@PathVariable("emial") String emial, Model model) {
        User userToEdit = userService.getUserByEmial(emial);
        model.addAttribute("userToEdit", userToEdit);
        return "editUser";
    }

    @GetMapping("/addToFavorite/{id}")
    public RedirectView addToFavorite(@PathVariable("id") Long id, Authentication authentication) {
        User loggedUser = userService.getUserByEmial(authentication.getName());
        userService.getAllUserFilms(loggedUser).add(filmService.getById(id));
        userService.saveUser(loggedUser);
        return new RedirectView("/myFilms");
    }

    @GetMapping("/myFilms")
    public Object allMyFilms(Model model, Authentication authentication) {
        try {
            User user = userService.getUserByEmial(authentication.getName());
            Set<Film> userFilms = userService.getAllUserFilms(user);
            model.addAttribute("userFilms", userFilms);
        } catch (NullPointerException ex) {
            return new RedirectView("/login");
        }
        return "myFilms";
    }

    @GetMapping("/removeFilm/{id}")
    public RedirectView removeFromFavorite(@PathVariable("id") Long id, Authentication authentication) {
        User user = userService.getUserByEmial(authentication.getName());
        userService.getAllUserFilms(user).remove(filmService.getById(id));
        userService.saveUser(user);
        return new RedirectView("/myFilms");
    }


    @GetMapping("/myFilms/{genre}")
    public String pickMyFilmsByGenre(@PathVariable("genre") Genre genre, Model model, Authentication authentication) {
        User user = userService.getUserByEmial(authentication.getName());
        Set<Film> userFilms = userService.getAllUserFilmsByGenre(user, genre);
        model.addAttribute("userFilms", userFilms);
        return "myFilms";
    }


}
