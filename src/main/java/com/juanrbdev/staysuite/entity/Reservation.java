package com.juanrbdev.staysuite.entity;
import com.juanrbdev.staysuite.emuns.ReservationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Getter
@Setter
@Table(name = "reservations")
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 👤 Relación con el Usuario (Huésped)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 🛏️ Relación con la Habitación específica
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    // 📅 Fecha de entrada (Llegada al hotel)
    @Column(name = "check_in", nullable = false)
    private LocalDate checkIn;

    // 📅 Fecha de salida (Día que deja la habitación)
    @Column(name = "check_out", nullable = false)
    private LocalDate checkOut;

    // 👥 Conteo real de personas para esta estancia (No puede exceder Room.capacity)
    @Column(name = "guest_count", nullable = false)
    private Integer guestCount;

    // 💰 Precio total (Calculado en Backend: precio_habitacion * noches)
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    // 📝 Estado: CONFIRMED, CANCELLED, COMPLETED, etc.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    // 🛋️ Campo extra para peticiones especiales de lujo (Opcional)
    @Column(length = 1000)
    private String specialRequests;
}