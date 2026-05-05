package com.juanrbdev.staysuite.dto;

import com.juanrbdev.staysuite.emuns.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomRequest{
        private String name;
        private RoomType type;
        private BigDecimal price;
}