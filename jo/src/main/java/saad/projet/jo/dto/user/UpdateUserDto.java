package saad.projet.jo.dto.user;
import jakarta.validation.constraints.*;

public class UpdateUserDto {
    @Email(message = "Veuillez fournir une adresse e-mail valide.")
    private String email;

    private String password;

    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Le nom complet ne doit contenir que des lettres, des chiffres et des espaces.")
    private String fullName;

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

}
