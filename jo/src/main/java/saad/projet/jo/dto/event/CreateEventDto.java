package saad.projet.jo.dto.event;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
public class CreateEventDto {

    @NotBlank
    private String categoryId;

    private String state;

    @NotBlank
    @Size(min=1 , max=25)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Le nom ne doit contenir que des lettres et des chiffres.")
    private String name;

    @NotNull(message = "Le nombre total de sièges ne doit pas être nul.")
    @PositiveOrZero(message = "Le nombre total de sièges doit être positif ou égal à zéro.")
    private Integer totalSeats;



    private Integer availableSeats;

    private Double standartPrice;


  //  @NotNull(message = "La date de l'événement ne doit pas être nulle.")
    @FutureOrPresent(message = "La date de l'événement doit être dans le présent ou dans le futur.")
    private LocalDate dateEvent;

    @NotNull(message = "L'heure de début ne doit pas être nulle.")
    private LocalTime hourBegin;

    @NotNull(message = "L'heure de fin ne doit pas être nulle.")
    private LocalTime hourEnding;

    private String shortDescription;

    private String longDescription;

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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }
}

