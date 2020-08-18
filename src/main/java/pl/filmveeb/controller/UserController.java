package pl.filmveeb.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import pl.filmveeb.model.Film;
import pl.filmveeb.model.User;
import pl.filmveeb.service.FilmService;
import pl.filmveeb.service.UserService;

import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registerUser")
    public ModelAndView registerUserForm() {
        return new ModelAndView("registerUser", "newUser", new User());
    }

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
    public ModelAndView editUser(@PathVariable("emial") String emial) {
        ModelAndView mav = new ModelAndView("editUser");
        User user = userService.getUserByEmial(emial);
        mav.addObject(user);
        return mav;
    }

//    @GetMapping("/myFilms")
//    public String myFilms(Authentication authentication) {
////        User user = (User) authentication.getPrincipal();
////        userService.getMyAllFilms(user);
//        return "myFilms";
//    }

}
