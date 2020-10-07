package pl.filmveeb.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.filmveeb.service.UserService;
import pl.filmveeb.service.WeatherService;

@Controller
public class IndexController {

    private final UserService userService;
    private final WeatherService weatherService;

    public IndexController(UserService userService, WeatherService weatherService) {
        this.userService = userService;
        this.weatherService = weatherService;
    }

    @GetMapping("/index")
    public ModelAndView index() {
        //todo can't log admin if is not in database
        ModelAndView mav = new ModelAndView("/index");
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && SecurityContextHolder.getContext().getAuthentication() != null
                && !(SecurityContextHolder.getContext().getAuthentication()
                instanceof AnonymousAuthenticationToken)) {
//            System.out.println("Jestem zalogowany");
            String firstName = userService.getLoggedUserDto().getFirstName();
            mav.addObject("firstName", firstName);
            String city = userService.getLoggedUserDto().getCity();
            mav.addObject("city", city);
            String cityTemperature = weatherService.getCityWeather();
            mav.addObject("cityTemperature", cityTemperature);
        }
        return mav;
    }
}
