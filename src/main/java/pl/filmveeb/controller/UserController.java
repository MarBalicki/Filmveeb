package pl.filmveeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.filmveeb.dto.UserDto;
import pl.filmveeb.model.Country;
import pl.filmveeb.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //todo possible only if no one are logged
    @GetMapping("/register")
    public ModelAndView registerUserForm() {
        ModelAndView mav = new ModelAndView("/register");
        mav.addObject("newUserDto", new UserDto());
        mav.addObject("countries", Country.values());
        return mav;
    }

    //todo possible only if no one are logged
    @PostMapping("/register")
    public String addUser(@Valid @ModelAttribute("newUserDto") UserDto userDto,
                          BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("newUserDto", userDto);
            model.addAttribute("countries", Country.values());
            return "register";
        }
        userService.addUser(userDto);
        return "redirect:/login";
    }

    @GetMapping("/profile/{email}")
    public ModelAndView userProfile(@PathVariable("email") String emial) {
        ModelAndView mav = new ModelAndView("/profile");
        UserDto currentUserDto = userService.getUserDtoByEmial(emial);
        mav.addObject("currentUserDto", currentUserDto);
        return mav;
    }

    @GetMapping("/editUser/{id}")
    public ModelAndView editUser(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("/editUser");
        UserDto userToEdit = userService.getUserDtoById(id);
        mav.addObject("userToEdit", userToEdit);
        mav.addObject("countries", Country.values());
        return mav;
    }

    @PostMapping("/updateUser")
    public String updateUser(@Valid @ModelAttribute("userToEdit") UserDto modelUser,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userToEdit", modelUser);
            model.addAttribute("countries", Country.values());
            return "editUser";
        }
        UserDto currentUserDto = userService.getLoggedUserDto();
        boolean sameEmails = userService.sameEmailAsCurrentUser(modelUser);
        userService.updateCurrentUser(modelUser);
        return sameEmails
                ? "redirect:/profile/" + currentUserDto.getEmail()
                : "redirect:/logout";
    }


}
