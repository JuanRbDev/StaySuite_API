package com.juanrbdev.staysuite.dto;

import com.juanrbdev.staysuite.emuns.RoomStatus;
import com.juanrbdev.staysuite.emuns.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomRequest{
        private String name;
        private String roomNumber;
        private RoomType type;
        private BigDecimal price;
        private RoomStatus status;
}