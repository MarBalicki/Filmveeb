package pl.filmveeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import pl.filmveeb.dto.UserDto;
import pl.filmveeb.model.Country;
import pl.filmveeb.model.User;
import pl.filmveeb.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView registerUserForm() {
        ModelAndView mav = new ModelAndView("/register");
        mav.addObject("newUserDto", new UserDto());
        mav.addObject("countries", Country.values());
        return mav;
    }

//    @GetMapping
//    public void getAllUsers(Model model) {
//        List<User> allUsers = userService.getAllUsers();
//        model.addAttribute("allUsers", allUsers);
//    }

    @PostMapping("/register")
    public RedirectView addUser(@ModelAttribute UserDto userDto) {
        userService.addUser(userDto);
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
    public String updateUser(@ModelAttribute User modelUser) {
        User currentUser = userService.getLoggedUser();
        boolean sameEmails = userService.sameEmails(currentUser, modelUser);
        if (userService.confirmCorrectPassword(modelUser.getPassword())) {
            userService.updateCurrentUser(currentUser, modelUser);
        }
        return sameEmails
                ? "redirect:/profile/" + currentUser.getEmail()
                : "redirect:/logout";
    }

}
