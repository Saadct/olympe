package saad.projet.jo.dto.user;

import saad.projet.jo.validator.StrongPassword;

public class UpdatePasswordDto {
 //   @StrongPassword
    private String oldPassword;

    @StrongPassword
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setnewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
