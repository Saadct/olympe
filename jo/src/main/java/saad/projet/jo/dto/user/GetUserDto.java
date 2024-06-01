package saad.projet.jo.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class GetUserDto {

    private String email;

    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public GetUserDto(String email, String fullName){
        this.email = email;
        this.fullName = fullName;
    }

}
