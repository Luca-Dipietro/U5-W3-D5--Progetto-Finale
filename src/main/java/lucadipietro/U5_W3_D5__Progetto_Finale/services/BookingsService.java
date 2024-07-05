package lucadipietro.U5_W3_D5__Progetto_Finale.services;

import lucadipietro.U5_W3_D5__Progetto_Finale.entities.Booking;
import lucadipietro.U5_W3_D5__Progetto_Finale.entities.Event;
import lucadipietro.U5_W3_D5__Progetto_Finale.entities.User;
import lucadipietro.U5_W3_D5__Progetto_Finale.exceptions.BadRequestException;
import lucadipietro.U5_W3_D5__Progetto_Finale.exceptions.NotFoundException;
import lucadipietro.U5_W3_D5__Progetto_Finale.payloads.BookingsDTO;
import lucadipietro.U5_W3_D5__Progetto_Finale.repositories.BookingsRepository;
import lucadipietro.U5_W3_D5__Progetto_Finale.repositories.EventsRepository;
import lucadipietro.U5_W3_D5__Progetto_Finale.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookingsService {

    @Autowired
    private BookingsRepository bookingsRepository;

    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private UsersRepository usersRepository;

    public Booking save(BookingsDTO body) {
        User user = usersRepository.findById(body.userId()).orElseThrow(() -> new NotFoundException(body.userId()));
        Event event = eventsRepository.findById(body.eventId()).orElseThrow(() -> new NotFoundException(body.eventId()));
        if(event.getMaxCapacity() <= bookingsRepository.findByEventId(body.eventId()).size()) {
            throw new BadRequestException("L'evento è sold out");
        }
        Booking booking = new Booking(user, event);
        return bookingsRepository.save(booking);
    }

    public List<Booking> findById(UUID userId) {
        return bookingsRepository.findByUserId(userId);
    }

    public void findByIdAndDelete(UUID bookingId) {
        Booking booking = bookingsRepository.findById(bookingId).orElseThrow(() -> new NotFoundException(bookingId));
        bookingsRepository.delete(booking);
    }
}
