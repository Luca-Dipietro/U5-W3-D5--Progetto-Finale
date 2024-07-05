package lucadipietro.U5_W3_D5__Progetto_Finale.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    public Booking(User user, Event event) {
        this.user = user;
        this.event = event;
    }
}
