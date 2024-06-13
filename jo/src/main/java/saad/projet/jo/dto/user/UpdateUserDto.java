package saad.projet.jo.dto.user;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

public class UpdateUserDto {
    @Email(message = "Veuillez fournir une adresse e-mail valide.")
    private String email;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Le nom complet ne doit contenir que des lettres, des chiffres et des espaces.")
    private String fullName;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Le nom complet ne doit contenir que des lettres, des chiffres et des espaces.")
    private String firstName;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Le nom complet ne doit contenir que des lettres, des chiffres et des espaces.")
    private String name;


    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
