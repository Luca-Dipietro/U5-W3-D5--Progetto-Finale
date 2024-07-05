package lucadipietro.U5_W3_D5__Progetto_Finale.repositories;

import lucadipietro.U5_W3_D5__Progetto_Finale.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventsRepository extends JpaRepository<Event, UUID> {
    Optional<Event> findByTitleAndLocation(String title, String location);
}
