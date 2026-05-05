package com.juanrbdev.staysuite.service;

import com.juanrbdev.staysuite.entity.Hotel;
import com.juanrbdev.staysuite.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    public Hotel crearHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

}
