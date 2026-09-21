package com.example.guesthousebookingsystem;

import com.example.guesthousebookingsystem.dtos.RoomDTO;
import com.example.guesthousebookingsystem.models.Room;
import com.example.guesthousebookingsystem.models.RoomType;
import com.example.guesthousebookingsystem.repositories.RoomRepository;
import com.example.guesthousebookingsystem.services.impl.RoomServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceImplTest {

    @Mock
    RoomRepository roomRepository;

    @InjectMocks
    RoomServiceImpl roomService;

    @Test
    void getAllRooms_shouldReturnListOfRoomDTOs() {
        Room room1 = new Room("101");
        room1.setId(1L);
        Room room2 = new Room("102");
        room2.setId(2L);

        when(roomRepository.findAll()).thenReturn(List.of(room1, room2));

        List<RoomDTO> result = roomService.getAllRooms();

        assertEquals(2, result.size());
        assertEquals("101", result.get(0).getName());
        assertEquals("102", result.get(1).getName());
    }

    @Test
    void getById_shouldReturnCorrectRoomDTO() {
        Room room = new Room("101");
        room.setId(1L);

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        RoomDTO result = roomService.getById(1L);

        assertEquals("101", result.getName());
        assertEquals(1L, result.getId());
    }

    @Test
    void save_shouldSaveRoom() {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setName("101");
        roomDTO.setRoomType(RoomType.SINGLE);
        roomDTO.setExtraBeds(0);
        roomService.save(roomDTO);

        Room expectedRoom = new Room();
        expectedRoom.setName("101");
        expectedRoom.setRoomType(RoomType.SINGLE);
        expectedRoom.setExtraBeds(0);
        verify(roomRepository).save(expectedRoom);
    }

    @Test
    void delete_shouldDeleteRoom() {
        roomService.delete(1L);

        verify(roomRepository).deleteById(1L);
    }
}