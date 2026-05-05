package com.juanrbdev.staysuite.service;

import com.juanrbdev.staysuite.dto.RoomRequest;
import com.juanrbdev.staysuite.dto.RoomResponse;
import com.juanrbdev.staysuite.emuns.RoomStatus;
import com.juanrbdev.staysuite.emuns.RoomType;
import com.juanrbdev.staysuite.entity.Hotel;
import com.juanrbdev.staysuite.entity.Room;
import com.juanrbdev.staysuite.repository.HotelRepository;
import com.juanrbdev.staysuite.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public RoomResponse createRoom(Long hotelId, RoomRequest request) {

        // 1. buscar hotel
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel no encontrado"));

        // 2. crear entidad
        Room room = new Room();
        room.setHotel(hotel);
        room.setName(request.getName());
        room.setType(request.getType());
        room.setPrice(request.getPrice());

        // 3. REGLAS DE NEGOCIO (TODO EN SERVICE)
        room.setCapacity(calculateCapacity(request.getType()));
        room.setStatus(RoomStatus.AVAILABLE);

        // 4. guardar
        Room saved = roomRepository.save(room);

        // 5. response mapping
        return mapToResponse(saved);
    }

    // 🔥 lógica centralizada
    private Integer calculateCapacity(RoomType type) {
        return switch (type) {
            case SINGLE -> 1;
            case DOUBLE -> 2;
            case SUITE -> 4;
        };
    }

    private RoomResponse mapToResponse(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getName(),
                room.getType(),
                room.getPrice(),
                room.getCapacity(),
                room.getStatus()
        );
    }


}
