package com.juanrbdev.staysuite.service;
import com.juanrbdev.staysuite.dto.ReservationRequest;
import com.juanrbdev.staysuite.dto.ReservationResponse;
import com.juanrbdev.staysuite.emuns.ReservationStatus;
import com.juanrbdev.staysuite.emuns.RoomStatus;
import com.juanrbdev.staysuite.entity.Reservation;
import com.juanrbdev.staysuite.entity.Room;
import com.juanrbdev.staysuite.entity.User;
import com.juanrbdev.staysuite.repository.ReservationRepository;
import com.juanrbdev.staysuite.repository.RoomRepository;
import com.juanrbdev.staysuite.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReservationResponse createReservation(ReservationRequest request, Long userId) {

        // 1. Validaciones básicas de fechas
        if (!request.getCheckOut().isAfter(request.getCheckIn())) {
            throw new RuntimeException("Error: La fecha de salida debe ser posterior a la de entrada.");
        }

        // 2. Buscar entidades
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


        // VALIDAR ESTADO DE LA HABITACIÓN
        if(room.getStatus() == RoomStatus.MAINTENANCE){
            throw new RuntimeException("La habitación está en mantenimiento");
        }

        if(room.getStatus() == RoomStatus.CLEANING){
            throw new RuntimeException("La habitación está en limpieza");
        }

        // 3. Validar capacidad de la habitación
        if (request.getGuestCount() > room.getCapacity()) {
            throw new RuntimeException("Lo sentimos, esta habitación solo permite " + room.getCapacity() + " personas.");
        }

        // 4. VERIFICAR DISPONIBILIDAD (Lógica de Solapamiento)
        // Buscamos si existe alguna reserva CONFIRMADA que choque con estas fechas
        boolean existsOverlap = reservationRepository.existsOverlap(
                room.getId(),
                request.getCheckIn(),
                request.getCheckOut()
        );

        if (existsOverlap) {
            throw new RuntimeException("La habitación ya está reservada para las fechas seleccionadas.");
        }

        // 5. CÁLCULO DE NOCHES Y PRECIO TOTAL
        long nights = ChronoUnit.DAYS.between(request.getCheckIn(), request.getCheckOut());
        BigDecimal totalAmount = room.getPrice().multiply(BigDecimal.valueOf(nights));

        // 6. Crear y persistir la reserva
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRoom(room);
        reservation.setCheckIn(request.getCheckIn());
        reservation.setCheckOut(request.getCheckOut());
        reservation.setGuestCount(request.getGuestCount());
        reservation.setTotalPrice(totalAmount);
        reservation.setStatus(ReservationStatus.CONFIRMED);

        if (request.getSpecialRequests() != null) {
            reservation.setSpecialRequests(request.getSpecialRequests());
        }


        Reservation saved = reservationRepository.save(reservation);

        return mapToResponse(saved);
    }

    private ReservationResponse mapToResponse(Reservation res) {
        return new ReservationResponse(
                res.getId(),
                res.getRoom().getRoomNumber(),
                res.getRoom().getName(),
                res.getCheckIn(),
                res.getCheckOut(),
                res.getGuestCount(),
                res.getTotalPrice(),
                res.getStatus(),
                res.getCreatedAt() // Viene de BaseEntity
        );
    }

    public List<ReservationResponse> getUserReservations(Long userId) {
        return reservationRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse) // Reutilizamos el mapeador que ya hicimos
                .toList();
    }








    @Transactional
    public ReservationResponse updateReservation(
            Long reservationId,
            ReservationRequest request,
            Long userId
    ) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        // seguridad básica
        if(!reservation.getUser().getId().equals(userId)){
            throw new RuntimeException("No puedes editar esta reserva");
        }

        Room room = reservation.getRoom();

        // validar fechas
        if(request.getCheckIn() != null && request.getCheckOut() != null){

            if(!request.getCheckOut().isAfter(request.getCheckIn())){
                throw new RuntimeException("Check-out inválido");
            }

            boolean overlap = reservationRepository.existsOverlap(
                    room.getId(),
                    request.getCheckIn(),
                    request.getCheckOut()
            );

            if(overlap){
                throw new RuntimeException("La habitación ya está ocupada");
            }

            reservation.setCheckIn(request.getCheckIn());
            reservation.setCheckOut(request.getCheckOut());

            long nights = ChronoUnit.DAYS.between(
                    request.getCheckIn(),
                    request.getCheckOut()
            );

            BigDecimal total = room.getPrice()
                    .multiply(BigDecimal.valueOf(nights));

            reservation.setTotalPrice(total);
        }

        if(request.getGuestCount() != null){

            if(request.getGuestCount() > room.getCapacity()){
                throw new RuntimeException("Capacidad excedida");
            }

            reservation.setGuestCount(request.getGuestCount());
        }

        if(request.getSpecialRequests() != null &&
                !request.getSpecialRequests().isBlank()){

            reservation.setSpecialRequests(request.getSpecialRequests());
        }

        Reservation updated = reservationRepository.save(reservation);

        return mapToResponse(updated);
    }


    @Transactional
    public void cancelReservation(Long reservationId, Long userId){

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if(!reservation.getUser().getId().equals(userId)){
            throw new RuntimeException("No puedes cancelar esta reserva");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);

        reservationRepository.save(reservation);
    }

}