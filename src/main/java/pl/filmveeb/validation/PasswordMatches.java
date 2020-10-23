package pl.filmveeb.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatches {
    String message() default "Incorrect password";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
