package saad.projet.jo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordConstraintValidator implements ConstraintValidator<StrongPassword, String> {

    public void initialize(StrongPassword strongPassword) {}

    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if(password == null){
            return false;
        }
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[?!;*@#$%^&-+=()])(?=\\S+$).{8,20}$";
        return password.matches(regex);
    }
}
