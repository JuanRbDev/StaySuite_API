package com.juanrbdev.staysuite.repository;

import com.juanrbdev.staysuite.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long> {
}
