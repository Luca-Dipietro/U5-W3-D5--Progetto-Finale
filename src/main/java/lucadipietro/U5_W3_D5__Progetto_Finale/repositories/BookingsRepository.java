package lucadipietro.U5_W3_D5__Progetto_Finale.repositories;

import lucadipietro.U5_W3_D5__Progetto_Finale.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingsRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findByUserId(UUID userId);
    List<Booking> findByEventId(UUID eventId);
}
