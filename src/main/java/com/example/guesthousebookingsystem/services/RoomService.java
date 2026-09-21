package com.example.guesthousebookingsystem.services;

import com.example.guesthousebookingsystem.dtos.RoomDTO;
import java.util.List;

public interface RoomService {
    List<RoomDTO> getAllRooms();
    RoomDTO getById(Long id);
    void save(RoomDTO roomsDTO);
    void delete(Long id);
}
