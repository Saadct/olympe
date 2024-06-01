package saad.projet.jo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private Double price;

    private String state;

    private String name;

    private String firstname;


    @ManyToOne
    @JoinColumn(name = "event_id")
    private Evenement evenement;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date = null;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateLastUpdate = null;

    public Ticket() {}

    public Ticket(String name,
                  String firstName,
                  Evenement event,
                  String state,
                  Double price,
                  User user,
                  LocalDateTime date

    )
    {
        this.name = name;
        this.firstname = firstName;
        this.evenement = event;
        this.state = state;
        this.price = price;
        this.user = user;
        this.date = date;
    }


    public Double getPrice() {
        return price;
    }

    public String getState() {
        return state;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFirstname() {
        return firstname;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public LocalDateTime getDateUpdate() {
        return dateLastUpdate;
    }

    public void setDateUpdate(LocalDateTime dateLastUpdate) {
        this.dateLastUpdate = dateLastUpdate;
    }


}
