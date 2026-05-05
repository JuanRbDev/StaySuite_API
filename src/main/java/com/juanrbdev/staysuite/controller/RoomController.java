package com.juanrbdev.staysuite.controller;

import com.juanrbdev.staysuite.dto.RoomRequest;
import com.juanrbdev.staysuite.dto.RoomResponse;
import com.juanrbdev.staysuite.entity.Room;
import com.juanrbdev.staysuite.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{hotelId}")
    public RoomResponse createRoom(
            @PathVariable Long hotelId,
            @RequestBody RoomRequest request
    ) {
        return roomService.createRoom(hotelId, request);
    }
}