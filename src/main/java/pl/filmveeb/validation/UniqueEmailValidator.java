package pl.filmveeb.validation;

import pl.filmveeb.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserService userService;

    public UniqueEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(UniqueEmail constraint) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (!userService.userIsLogged()) {
            return userService.userNotExists(email);
        }
        return true;
    }
}
