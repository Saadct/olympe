package saad.projet.jo.dto.ticket;

import saad.projet.jo.model.Event;
import saad.projet.jo.model.Event;

public class GetTicketDto {

    private String uuid;

    private String name;

    private Double price;

    private String state;

    private String UserUuid;


    private String firstname;

    private Event evenement;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public Event getEvenement() {
        return evenement;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setEvenement(Event evenement) {
        this.evenement = evenement;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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

    public String getUserUuid() {
        return UserUuid;
    }

    public void setUserUuid(String userUuid) {
        UserUuid = userUuid;
    }
}

