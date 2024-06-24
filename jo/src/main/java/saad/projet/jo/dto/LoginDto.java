package saad.projet.jo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import saad.projet.jo.validator.StrongPassword;

public class LoginDto {

    @Email(message = "Veuillez fournir une adresse e-mail valide.")
    private String email;

    @StrongPassword
    private String password;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "Le nom ne doit contenir que des lettres.")
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