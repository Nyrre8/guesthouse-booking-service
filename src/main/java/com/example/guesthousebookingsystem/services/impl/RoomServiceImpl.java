package com.example.guesthousebookingsystem.services.impl;

import com.example.guesthousebookingsystem.dtos.RoomDTO;
import com.example.guesthousebookingsystem.models.Room;
import com.example.guesthousebookingsystem.repositories.RoomRepository;
import com.example.guesthousebookingsystem.services.RoomService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private static final Logger log = LoggerFactory.getLogger(RoomServiceImpl.class);

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(r -> new RoomDTO(r.getName(), r.getId(), r.getRoomType(),
                        r.getExtraBeds(), r.getMaxCapacity()))
                .toList();
    }

    @Override
    public RoomDTO getById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow();
        return new RoomDTO(room.getName(), room.getId(), room.getRoomType(),
                room.getExtraBeds(), room.getMaxCapacity());


    }

    @Override
    public void save(RoomDTO roomDTO) {
        Room room = new Room();
        room.setId(roomDTO.getId());
        room.setName(roomDTO.getName());
        room.setRoomType(roomDTO.getRoomType());

        if ("SINGLE".equals(String.valueOf(roomDTO.getRoomType()))) {
            room.setExtraBeds(0);
        } else {
            room.setExtraBeds(roomDTO.getExtraBeds());
        }
        Room saved = roomRepository.save(room);
        log.info("Room {} saved (type {}, extra beds {})",
                saved.getId(), saved.getRoomType(), saved.getExtraBeds());
    }
    @Override
    public void delete(Long id) {
        roomRepository.deleteById(id);log.info("Room {} deleted", id);
    }
}
