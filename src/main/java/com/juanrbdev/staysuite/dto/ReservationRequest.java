package com.juanrbdev.staysuite.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ReservationRequest {
    @NotNull
    private Long roomId;

    @NotNull
    @FutureOrPresent(message = "La fecha de entrada no puede ser en el pasado")
    private LocalDate checkIn;

    @NotNull
    @Future(message = "La fecha de salida debe ser en el futuro")
    private LocalDate checkOut;

    @NotNull
    @Min(value = 1, message = "Debe haber al menos 1 huésped")
    private Integer guestCount;

    private String specialRequests;
}
