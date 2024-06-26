package saad.projet.jo.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CategoryDto {

    @NotBlank
    @Size(min=1 , max=25)
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "Le nom ne doit contenir que des lettres, des chiffres et des espaces.")
    private String name;

    @NotBlank
    @Size(min=1 , max=25)
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "Le type ne doit contenir que des lettres, des chiffres et des espaces.")
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
