package pl.filmveeb.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordsFieldMatchValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordsMatchValue {

    String field();
    String fieldMatch();
    String message() default "Password do not match!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
