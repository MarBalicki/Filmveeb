package pl.filmveeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import pl.filmveeb.dto.FilmDto;
import pl.filmveeb.dto.UserDto;
import pl.filmveeb.model.Genre;
import pl.filmveeb.service.FilmService;
import pl.filmveeb.service.UserService;

import java.util.Set;

@Controller
public class UserFilmsController {

    private final UserService userService;
    private final FilmService filmService;

    public UserFilmsController(UserService userService, FilmService filmService) {
        this.userService = userService;
        this.filmService = filmService;
    }

    @PostMapping("/addToFavorite/{id}")
    public String addFilmToFavorite(@PathVariable("id") Long filmId) {
        if (userService.findByEmial(userService.getLoggedUserDto().getEmail()).isEmpty()) {
            return "redirect:/login";
        } else {
            filmService.addToFavorite(filmId);
            return "redirect:/userFilms";
        }
    }

    @PostMapping("/removeFilm/{id}")
    public RedirectView removeFromFavorite(@PathVariable("id") Long filmId) {
        filmService.removeFromFavorite(filmId);
        return new RedirectView("/userFilms");
    }

    @GetMapping("/userFilms")
    public String allMyFilms(Model model) {
        if (userService.findByEmial(userService.getLoggedUserDto().getEmail()).isEmpty()) {
            return "redirect:/login";
        } else {
            UserDto loggedUserDto = userService.getLoggedUserDto();
            String fullName = loggedUserDto.getFirstName() + " " + loggedUserDto.getLastName();
            model.addAttribute("fullName", fullName);
            Set<FilmDto> userFilmsDto = filmService.getCurrentUserAllFilms();
            model.addAttribute("userFilmsDto", userFilmsDto);
            return "/userFilms";
        }
    }

    @GetMapping("/userFilms/{genre}")
    public ModelAndView pickMyFilmsByGenre(@PathVariable("genre") Genre genre) {
        ModelAndView mav = new ModelAndView("/userFilms");
        Set<FilmDto> userFilmsDto = filmService.getAllUserFilmsByGenre(genre);
        mav.addObject("userFilmsDto", userFilmsDto);
        UserDto loggedUserDto = userService.getLoggedUserDto();
        String fullNameAndGenre = loggedUserDto.getFirstName() + " " + loggedUserDto.getLastName() + " w kategorii " + genre;
        mav.addObject("fullName", fullNameAndGenre);
        return mav;
    }


}
