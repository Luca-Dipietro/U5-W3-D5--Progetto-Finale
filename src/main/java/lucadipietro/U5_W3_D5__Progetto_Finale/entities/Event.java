package lucadipietro.U5_W3_D5__Progetto_Finale.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String title;
    private String description;
    private LocalDate date;
    private String location;
    @Column(name = "max_capacity")
    private int maxCapacity;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Event(String title, String description, LocalDate date, String location, int maxCapacity, User user) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.maxCapacity = maxCapacity;
        this.user = user;
    }
}
