package com.juanrbdev.staysuite.emuns;
import lombok.*;

@Getter
public enum RoomType {
    SERENITY(1, 1, 1),
    HEAVEN(2, 1, 1),
    SUITE(4, 2, 2),
    GRAND_RESIDENCE(6, 3, 3);

    private final int capacity;
    private final int bedrooms;
    private final int bathrooms;

    RoomType(int capacity, int bedrooms, int bathrooms) {
        this.capacity = capacity;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
    }
}