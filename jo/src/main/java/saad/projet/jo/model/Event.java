package saad.projet.jo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;
    private String name;
    private String shortDescription;
    private String longDescription;
    private Integer totalSeats;
    private Integer availableSeats;
    private LocalDate dateEvent;
    private LocalTime hourBegin;
    private LocalTime hourEnding;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_Id")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="event_id")
    private List<Ticket> tickets = new ArrayList<>();

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}
