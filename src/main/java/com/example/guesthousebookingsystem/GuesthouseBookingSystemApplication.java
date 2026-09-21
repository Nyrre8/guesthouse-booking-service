package com.example.guesthousebookingsystem;

import com.example.guesthousebookingsystem.models.Room;
import com.example.guesthousebookingsystem.models.RoomType;
import com.example.guesthousebookingsystem.repositories.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GuesthouseBookingSystemApplication {

    public static void main(String[] args) {SpringApplication.run(GuesthouseBookingSystemApplication.class, args);}

    @Bean
    public CommandLineRunner demo(RoomRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Room(null,"A1", RoomType.SINGLE, 0));
                repo.save(new Room(null, "A2", RoomType.DOUBLE, 1));
                repo.save(new Room(null, "A3", RoomType.DOUBLE, 2));


            }
        };
    }
}
