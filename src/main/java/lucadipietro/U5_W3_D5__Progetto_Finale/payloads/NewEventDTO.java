package lucadipietro.U5_W3_D5__Progetto_Finale.payloads;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NewEventDTO(
        @NotEmpty(message = "Il titolo è un dato obbligatorio")
        String title,
        @NotEmpty(message = "La descrizione è un dato obbligatorio")
        String description,
        @NotEmpty(message = "La data è un dato obbligatorio")
        String date,
        @NotEmpty(message = "Il luogo è un dato obbligatorio")
        String location,
        @NotNull
        int maxCapacity,
        @NotNull
        UUID userId)
{}
