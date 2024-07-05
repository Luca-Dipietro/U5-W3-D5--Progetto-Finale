package lucadipietro.U5_W3_D5__Progetto_Finale.controllers;

import lucadipietro.U5_W3_D5__Progetto_Finale.entities.Booking;
import lucadipietro.U5_W3_D5__Progetto_Finale.exceptions.BadRequestException;
import lucadipietro.U5_W3_D5__Progetto_Finale.payloads.BookingsDTO;
import lucadipietro.U5_W3_D5__Progetto_Finale.services.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingsController {
    @Autowired
    private BookingsService bookingsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Booking saveBooking(@RequestBody @Validated BookingsDTO body, BindingResult validationResult) {
        if(validationResult.hasErrors()){
            throw new BadRequestException(validationResult.getAllErrors());
        } else {
            return this.bookingsService.save(body);
        }
    }

    @GetMapping("/user/{userId}")
    public List<Booking> findById(@PathVariable UUID userId) {
        return bookingsService.findById(userId);
    }

    @DeleteMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID bookingId) {
        bookingsService.findByIdAndDelete(bookingId);
    }
}
