package saad.projet.jo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import saad.projet.jo.validator.StrongPassword;

public class LoginDto {

    @Email(message = "Veuillez fournir une adresse e-mail valide.")
    private String email;

    @StrongPassword
    private String password;

    @Size(min=1 , max=25)
    @Pattern(regexp = "^[a-zA-Z0-9 À-ÖØ-öø-ÿ'’]*$", message = "Le nom ne doit contenir que des lettres, des chiffres et des espaces.")
    private String fullName;


    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

}