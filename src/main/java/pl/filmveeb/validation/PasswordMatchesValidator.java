package pl.filmveeb.validation;

import pl.filmveeb.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, String> {

    private final Logger log = Logger.getLogger(getClass().getName());
    private final UserService userService;

    public PasswordMatchesValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(PasswordMatches constraint) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        try {
            if (userService.userIsLogged()) {
                return userService.passwordMatchToCurrentUser(password);
            }
        } catch (RuntimeException e) {
            log.log(Level.WARNING, "No one are logged, it's a registration template!");
        }
        return true;
    }
}
