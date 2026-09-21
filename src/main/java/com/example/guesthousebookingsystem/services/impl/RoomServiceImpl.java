package com.example.guesthousebookingsystem.services.impl;

import com.example.guesthousebookingsystem.dtos.RoomDTO;
import com.example.guesthousebookingsystem.models.Room;
import com.example.guesthousebookingsystem.repositories.RoomRepository;
import com.example.guesthousebookingsystem.services.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

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
        roomRepository.save(room);
    }

/*
    I save(...) sätts värdet rakt av: room.setExtraBeds(roomDTO.getExtraBeds()); (rad 45).
            ◦
    Här bör du lägga affärsregeln, t.ex. om roomType == SINGLE så sätt extraBeds = 0 eller kasta valideringsfel.

*/
    @Override
    public void delete(Long id) {
        roomRepository.deleteById(id);
    }
}
