package com.juanrbdev.staysuite.controller;

import com.juanrbdev.staysuite.dto.RoomRequest;
import com.juanrbdev.staysuite.dto.RoomResponse;
import com.juanrbdev.staysuite.entity.Room;
import com.juanrbdev.staysuite.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;


    @PostMapping("/{hotelId}")
    @ResponseStatus(HttpStatus.CREATED) // Devuelve 201 en lugar de 200
    public RoomResponse createRoom(
            @PathVariable Long hotelId,
            @Validated @RequestBody RoomRequest request // Valida los datos de entrada
    ) {
        return roomService.createRoom(hotelId, request);
    }


    @PutMapping("/{roomId}")
    public ResponseEntity<RoomResponse> updateRoom(
            @PathVariable Long roomId,
            @RequestBody RoomRequest request
    ){

        return ResponseEntity.ok(
                roomService.updateRoom(roomId, request)
        );
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long roomId){

        roomService.deleteRoom(roomId);
        return ResponseEntity.ok("Habitación eliminada");
    }


}