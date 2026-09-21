package com.example.guesthousebookingsystem.dtos;

import com.example.guesthousebookingsystem.models.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private String name;
    private Long id;
    private RoomType roomType;
    private int extraBeds;
    private int maxCapacity;
}