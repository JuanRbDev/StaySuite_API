package com.juanrbdev.staysuite.repository;

import com.juanrbdev.staysuite.entity.Hotel;
import com.juanrbdev.staysuite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByName(String name);
}
