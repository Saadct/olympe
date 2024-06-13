package saad.projet.jo.dto.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import saad.projet.jo.constants.Role;

public class GetUserDto {

    private String uuid;

    private String email;

    private String fullName;

    private Role role;

     private String firstName;

    private String name;


    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getUuid() {
        return uuid;
    }

    public GetUserDto(String email, String fullName, String uuid, Role role, String name, String firstName){
        this.email = email;
        this.fullName = fullName;
        this.uuid = uuid;
        this.role = role;
        this.firstName = firstName;
        this.name = name;
    }


    public Role getRole() {
        return role;
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
