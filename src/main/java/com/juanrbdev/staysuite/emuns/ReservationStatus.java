package com.juanrbdev.staysuite.emuns;

public enum ReservationStatus {
    CONFIRMED, // Pagada o garantizada
    PENDING,   // Esperando pago
    CANCELLED, // El usuario la canceló
    COMPLETED  // El huésped ya se fue (Check-out hecho)
}