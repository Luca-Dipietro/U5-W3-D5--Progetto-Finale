package lucadipietro.U5_W3_D5__Progetto_Finale.services;

import lucadipietro.U5_W3_D5__Progetto_Finale.entities.Event;
import lucadipietro.U5_W3_D5__Progetto_Finale.exceptions.BadRequestException;
import lucadipietro.U5_W3_D5__Progetto_Finale.exceptions.NotFoundException;
import lucadipietro.U5_W3_D5__Progetto_Finale.payloads.NewEventDTO;
import lucadipietro.U5_W3_D5__Progetto_Finale.repositories.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class EventsService {
    @Autowired
    private EventsRepository eventsRepository;
    @Autowired
    private UsersService usersService;

    public Page<Event> getEvent(int pageNumber, int pageSize, String sortBy){
        if(pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        return eventsRepository.findAll(pageable);
    }

    public Event save(NewEventDTO body){
        this.eventsRepository.findByTitleAndLocation(body.title(), body.location()).ifPresent(
                event -> {
                    throw new BadRequestException("Esiste già un evento a " + body.location() +  " con questo titolo " + body.title());
                }
        );
        Event newEvent = new Event(body.title(), body.description(), LocalDate.parse(body.date()), body.location(), body.maxCapacity(),usersService.findById(body.userId()) );
        return this.eventsRepository.save(newEvent);
    }

    public Event findById(UUID eventId) {
        return this.eventsRepository.findById(eventId).orElseThrow(() -> new NotFoundException(eventId));
    }

    public Event findByIdAndUpdate(UUID eventId, NewEventDTO body){
        Event found = this.findById(eventId);
        found.setTitle(body.title());
        found.setDescription(body.description());
        found.setDate(LocalDate.parse(body.date()));
        found.setLocation(body.location());
        found.setMaxCapacity(body.maxCapacity());
        return this.eventsRepository.save(found);
    }

    public void findByIdAndDelete(UUID eventId) {
        Event found = this.findById(eventId);
        this.eventsRepository.delete(found);
    }

}
