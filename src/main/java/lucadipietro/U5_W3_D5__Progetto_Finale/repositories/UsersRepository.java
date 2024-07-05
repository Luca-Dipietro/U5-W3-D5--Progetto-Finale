package lucadipietro.U5_W3_D5__Progetto_Finale.repositories;

import lucadipietro.U5_W3_D5__Progetto_Finale.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
