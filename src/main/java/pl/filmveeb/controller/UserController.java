package pl.filmveeb.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import pl.filmveeb.model.Film;
import pl.filmveeb.model.Genre;
import pl.filmveeb.model.Rate;
import pl.filmveeb.model.User;
import pl.filmveeb.repository.RateRepository;
import pl.filmveeb.service.FilmService;
import pl.filmveeb.service.UserService;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;
    private final FilmService filmService;
    private final RateRepository rateRepository;

    public UserController(UserService userService, FilmService filmService, RateRepository rateRepository) {
        this.userService = userService;
        this.filmService = filmService;
        this.rateRepository = rateRepository;
    }

    @GetMapping("/register")
    public ModelAndView registerUserForm() {
        return new ModelAndView("register", "newUser", new User());
    }

//    @GetMapping
//    public void getAllUsers(Model model) {
//        List<User> allUsers = userService.getAllUsers();
//        model.addAttribute("allUsers", allUsers);
//    }

    @PostMapping("/register")
    public RedirectView addUser(@ModelAttribute User user) {
        userService.addUser(user);
        return new RedirectView("/login");
    }

    @GetMapping("/profile/{email}")
    public ModelAndView userProfile(@PathVariable("email") String emial) {
        ModelAndView mav = new ModelAndView("/profile");
        User currentUser = userService.getUserByEmial(emial);
        mav.addObject("currentUser", currentUser);
        return mav;
    }

    @GetMapping("/editUser/{emial}")
    public ModelAndView editUser(@PathVariable("emial") String emial) {
        ModelAndView mav = new ModelAndView("/editUser");
        User userToEdit = userService.getUserByEmial(emial);
        mav.addObject("userToEdit", userToEdit);
        return mav;
    }

    @PostMapping("/updateUser")
    public RedirectView updateUser(@ModelAttribute User user) {
        //not sure is it safe
        if (userService.confirmCorrectPassword(user.getPassword())) {
            userService.saveUser(user);
        }
        return new RedirectView("/profile/" + user.getEmail());
    }

    @GetMapping("/addToFavorite/{id}")
    public RedirectView addToFavorite(@PathVariable("id") Long id) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.getUserByEmial(userName);
        userService.getAllUserFilms(currentUser).add(filmService.getById(id));
        userService.saveUser(currentUser);
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
        //todo
        User user = userService.getUserByEmial(authentication.getName());
//        Film film = userService.getOneFilm(user, filmService.findById(id));
        Set<Film> films = userService.getAllUserFilms(user);
        Optional<Film> film = films.stream().reduce((film1, film2) -> filmService.getById(id));
        if (film.isPresent()) {
            Film currentFilm = film.get();
            currentFilm.getRates().stream().filter(rate -> rate.getFilm() == currentFilm).findFirst();
            Rate rate = new Rate(10, new Date(),user,currentFilm);
            rateRepository.save(rate);
        }
//        userService.getAllUserFilms(user).remove(filmService.getById(id));
//        userService.saveUser(user);
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
