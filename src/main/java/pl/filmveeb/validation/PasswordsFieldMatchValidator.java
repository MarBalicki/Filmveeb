package pl.filmveeb.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsFieldMatchValidator implements ConstraintValidator<PasswordsMatchValue, Object> {

    private String password;
    private String passwordConfirm;

    @Override
    public void initialize(PasswordsMatchValue constraint) {
        this.password = constraint.field();
        this.passwordConfirm = constraint.fieldMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object passwordValue = new BeanWrapperImpl(value).getPropertyValue(password);
        Object passwordConfirmValue = new BeanWrapperImpl(value).getPropertyValue(passwordConfirm);
        if (passwordValue != null) {
            return passwordValue.equals(passwordConfirmValue);
        } else {
            return passwordConfirmValue == null;
        }
    }
}
