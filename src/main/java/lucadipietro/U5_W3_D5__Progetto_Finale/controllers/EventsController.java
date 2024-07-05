package lucadipietro.U5_W3_D5__Progetto_Finale.controllers;

import lucadipietro.U5_W3_D5__Progetto_Finale.entities.Event;
import lucadipietro.U5_W3_D5__Progetto_Finale.exceptions.BadRequestException;
import lucadipietro.U5_W3_D5__Progetto_Finale.payloads.NewEventDTO;
import lucadipietro.U5_W3_D5__Progetto_Finale.services.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventsController {
    @Autowired
    private EventsService eventsService;

    @GetMapping
    public Page<Event> getAllEvent(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy){
        return this.eventsService.getEvent(page,size,sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('EVENT_ORGANIZER') or hasAuthority('ADMIN')")
    public Event saveEvent(@RequestBody @Validated NewEventDTO body, BindingResult validationResult){
        if(validationResult.hasErrors()){
            throw new BadRequestException(validationResult.getAllErrors());
        } else {
            return this.eventsService.save(body);
        }
    }

    @GetMapping("/{eventId}")
    public Event findById(@PathVariable UUID eventId){
        return this.eventsService.findById(eventId);
    }

    @PutMapping("/{eventId}")
    @PreAuthorize("hasAuthority('EVENT_ORGANIZER') or hasAuthority('ADMIN')")
    public Event findByIdAndUpdate(@PathVariable UUID eventId, @RequestBody @Validated NewEventDTO body, BindingResult validationResult){
        if(validationResult.hasErrors()){
            throw new BadRequestException(validationResult.getAllErrors());
        }else {
            return this.eventsService.findByIdAndUpdate(eventId,body);
        }
    }

    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('EVENT_ORGANIZER') or hasAuthority('ADMIN')")
    public void findByIdAndDelete(@PathVariable UUID eventId){
        this.eventsService.findByIdAndDelete(eventId);
    }
}
