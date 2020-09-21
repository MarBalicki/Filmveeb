package pl.filmveeb.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.filmveeb.model.User;
import pl.filmveeb.service.UserService;

@Controller
public class IndexController {

    private final UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public ModelAndView index() {
        //todo can't log admin
        ModelAndView mav = new ModelAndView("/index");
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
        && SecurityContextHolder.getContext().getAuthentication() != null) {
//            System.out.println("Jestem zalogowany");
            String firstName = userService.getLoggedUser().getFirstName();
            mav.addObject("firstName", firstName);
        }
        return mav;
    }
}
