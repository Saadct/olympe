package saad.projet.jo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private String type;

    private LocalDateTime date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    public Operation() {}

    public Operation(String type, LocalDateTime date, User user){
        this.type = type;
        this.date = date;
        this.user = user;
    }


    public String getUuid() {
        return uuid;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime  getDate() {
        return date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(LocalDateTime  date) {
        this.date = date;
    }
}
