package pl.filmveeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import pl.filmveeb.model.User;
import pl.filmveeb.service.UserService;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registerUser")
    public ModelAndView registerUserForm() {
        return new ModelAndView("registerUser", "newUser", new User());
    }

    @PostMapping("/addUser")
    public RedirectView addUser(@ModelAttribute User user) {
        userService.save(user);
        return new RedirectView("index");
    }
}
