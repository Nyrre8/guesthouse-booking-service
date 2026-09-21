package com.example.guesthousebookingsystem.repositories;

import com.example.guesthousebookingsystem.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
