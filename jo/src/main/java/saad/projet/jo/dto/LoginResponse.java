package saad.projet.jo.dto;

import saad.projet.jo.constants.Role;

public class LoginResponse {

    private String token;
    private long expiresIn;

    private String id;

    private Role role;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Role getRole() {
        return role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
