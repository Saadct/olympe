package saad.projet.jo.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private String name;

    private String type;


    @OneToMany
    @JoinColumn(name="category_Id")
    private List<Evenement> evenements = new ArrayList<>();

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

}
