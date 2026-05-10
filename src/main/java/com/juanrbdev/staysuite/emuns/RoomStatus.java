package com.juanrbdev.staysuite.emuns;

public enum RoomStatus {
    AVAILABLE,   // Lista para que entre alguien ya mismo
    CLEANING,    // El huésped salió, pero falta que pase limpieza
    MAINTENANCE, // Fuera de servicio por reparaciones
    OCCUPIED
}
