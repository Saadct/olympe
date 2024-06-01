package saad.projet.jo.dto.user;

import saad.projet.jo.validator.StrongPassword;

public class UpdatePasswordDto {
    @StrongPassword
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
