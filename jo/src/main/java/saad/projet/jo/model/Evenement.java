package saad.projet.jo.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Evenement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;
    private String state;
    private String name;
    private String shortDescription;
    private String longDescription;
    private Integer totalSeats;
    private Integer availableSeats;
    private Double standartPrice;
    private LocalDate dateEvent;
    private LocalTime hourBegin;
    private LocalTime hourEnding;
    private LocalDateTime dateCreate;
    private LocalDateTime dateLastUpdate;

    @ManyToOne
    @JoinColumn(name = "sport_id")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="event_id")
    private List<Ticket> tickets = new ArrayList<>();

    public Double getStandartPrice() {
        return standartPrice;
    }

    public void setStandartPrice(Double standartPrice) {
        standartPrice = standartPrice;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public String getUuid() {
        return uuid;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public LocalDateTime getDateLastUpdate() {
        return dateLastUpdate;
    }

    public void setDateLastUpdate(LocalDateTime dateLastUpdate) {
        this.dateLastUpdate = dateLastUpdate;
    }

    public LocalTime getHourBegin() {
        return hourBegin;
    }

    public LocalTime getHourEnding() {
        return hourEnding;
    }

    public LocalDate getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(LocalDate dateEvent) {
        this.dateEvent = dateEvent;
    }

    public void setHourBegin(LocalTime hourBegin) {
        this.hourBegin = hourBegin;
    }

    public void setHourEnding(LocalTime hourEnding) {
        this.hourEnding = hourEnding;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
