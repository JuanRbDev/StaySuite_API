package com.juanrbdev.staysuite.controller;

import com.juanrbdev.staysuite.dto.ReservationRequest;
import com.juanrbdev.staysuite.dto.ReservationResponse;
import com.juanrbdev.staysuite.entity.User;
import com.juanrbdev.staysuite.repository.UserRepository;
import com.juanrbdev.staysuite.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final UserRepository userRepository;

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse createReservation(
            @PathVariable Long userId,
            @Valid @RequestBody ReservationRequest request
    ) {
        return reservationService.createReservation(request, userId);
    }

    @GetMapping("/user/{userId}")
    public List<ReservationResponse> getMyReservations(@PathVariable Long userId) {
        return reservationService.getUserReservations(userId);
    }


    @PutMapping("/{reservationId}")
    public ResponseEntity<ReservationResponse> updateReservation(
            @PathVariable Long reservationId,
            @RequestBody ReservationRequest request,
            Authentication auth
    ){

        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow();

        return ResponseEntity.ok(
                reservationService.updateReservation(
                        reservationId,
                        request,
                        user.getId()
                )
        );
    }

    @PatchMapping("/{reservationId}/cancel")
    public ResponseEntity<String> cancelReservation(
            @PathVariable Long reservationId,
            Authentication auth
    ){

        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow();

        reservationService.cancelReservation(
                reservationId,
                user.getId()
        );

        return ResponseEntity.ok("Reserva cancelada");
    }


}