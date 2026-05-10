package com.juanrbdev.staysuite.service;
import com.juanrbdev.staysuite.dto.RoomRequest;
import com.juanrbdev.staysuite.dto.RoomResponse;
import com.juanrbdev.staysuite.emuns.RoomStatus;
import com.juanrbdev.staysuite.emuns.RoomType;
import com.juanrbdev.staysuite.entity.Hotel;
import com.juanrbdev.staysuite.entity.Room;
import com.juanrbdev.staysuite.repository.HotelRepository;
import com.juanrbdev.staysuite.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    @Transactional
    public RoomResponse createRoom(Long hotelId, RoomRequest request) {

        // 1. Buscar el hotel al que pertenecerá la habitación
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel no encontrado con ID: " + hotelId));

        // 2. Crear y configurar la entidad Room
        Room room = new Room();
        room.setHotel(hotel);
        room.setName(request.getName());
        room.setRoomNumber(request.getRoomNumber());
        room.setPrice(request.getPrice());

        // 3. REGLAS DE NEGOCIO AUTOMATIZADAS DESDE EL ENUM
        // Extraemos el tipo del request para acceder a sus propiedades predefinidas
        RoomType type = request.getType();
        room.setType(type);

        // Seteamos los valores estructurales automáticos del Enum corregido
        room.setCapacity(type.getCapacity());
        room.setNumBedrooms(type.getBedrooms());
        room.setNumBathrooms(type.getBathrooms());

        // Estado inicial por defecto
        room.setStatus(RoomStatus.AVAILABLE);

        // 4. Guardar en la base de datos (MySQL)
        Room saved = roomRepository.save(room);

        // 5. Mapear a la respuesta DTO
        return mapToResponse(saved);
    }

    private RoomResponse mapToResponse(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getName(),
                room.getType(),
                room.getPrice(),
                room.getCapacity(),
                room.getNumBathrooms(),
                room.getNumBedrooms(),
                room.getStatus()
        );
    }



    @Transactional
    public RoomResponse updateRoom(Long roomId, RoomRequest request){

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

        if(request.getName() != null && !request.getName().isBlank()){
            room.setName(request.getName());
        }

        if(request.getRoomNumber() != null && !request.getRoomNumber().isBlank()){
            room.setRoomNumber(request.getRoomNumber());
        }

        if(request.getPrice() != null){
            room.setPrice(request.getPrice());
        }

        // Si cambia el tipo
        if(request.getType() != null){

            RoomType type = request.getType();

            room.setType(type);

            // actualizar automáticamente
            room.setCapacity(type.getCapacity());
            room.setNumBedrooms(type.getBedrooms());
            room.setNumBathrooms(type.getBathrooms());
        }

        if(request.getStatus() != null){
            room.setStatus(request.getStatus());
        }

        Room updated = roomRepository.save(room);

        return mapToResponse(updated);
    }

    @Transactional
    public void deleteRoom(Long roomId){

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

        roomRepository.delete(room);
    }

}