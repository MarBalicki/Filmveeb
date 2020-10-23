package pl.filmveeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.filmveeb.dto.NewPasswordDto;
import pl.filmveeb.dto.ResetPasswordDto;
import pl.filmveeb.service.UserService;

@Controller
@RequestMapping("/user")
public class ResetPasswordController {

    private final UserService userService;

    public ResetPasswordController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/resetPassword")
    public ModelAndView resetPasswordForm() {
        return new ModelAndView("/resetPassword", "resetPasswordDto", new ResetPasswordDto());
    }

    @PostMapping("/resetPassword")
    public String getResetLink(ResetPasswordDto resetPasswordDto) {
        userService.sendResetLink(resetPasswordDto);
        return "login";
    }

    @GetMapping("/changePasswordForm/{token}")
    public ModelAndView changePasswordForm(@PathVariable("token") String token) {
        ModelAndView mav = new ModelAndView("/changePassword");
        NewPasswordDto newPasswordDto = new NewPasswordDto();
        newPasswordDto.setToken(token);
        mav.addObject("newPasswordDto", newPasswordDto);
        return mav;
    }

    @PostMapping("/changePassword")
    public String changePassword(NewPasswordDto newPasswordDto) {
        userService.changePassword(newPasswordDto);
        return "login";
    }


}
