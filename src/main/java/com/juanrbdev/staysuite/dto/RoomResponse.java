package com.juanrbdev.staysuite.dto;

import com.juanrbdev.staysuite.emuns.RoomStatus;
import com.juanrbdev.staysuite.emuns.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class RoomResponse {
    private Long id;
    private String name;
    private RoomType type;
    private BigDecimal price;
    private Integer capacity;
    private RoomStatus status;

}