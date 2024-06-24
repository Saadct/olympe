package saad.projet.jo.dto.category;

import jakarta.validation.constraints.Pattern;

public class CategoryDto {

    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "Le nom complet ne doit contenir que des lettres, des chiffres et des espaces.")
    private String name;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "Le nom complet ne doit contenir que des lettres.")
    private String type;


    public String getName() {
        return name;
    }

    public void setName(String name) {
    }

    public CategoryDto(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
