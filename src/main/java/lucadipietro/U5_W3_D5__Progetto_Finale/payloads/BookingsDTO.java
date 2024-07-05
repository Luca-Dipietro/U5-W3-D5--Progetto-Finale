package lucadipietro.U5_W3_D5__Progetto_Finale.payloads;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BookingsDTO(
        @NotNull
        UUID userId,
        @NotNull
        UUID eventId)
{}
