package com.salas.config;

import com.salas.dto.ReservationDTO;
import com.salas.dto.RoomDTO;
import com.salas.model.Reservation;
import com.salas.model.Room;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper defaultMapper() {
        return new ModelMapper();
    }

    @Bean
    public ModelMapper roomMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.createTypeMap(RoomDTO.class, Room.class)
                .addMapping(RoomDTO::getNumberDto, Room::setNumber)
                .addMapping(RoomDTO::getTypeDto, Room::setType)
                .addMapping(RoomDTO::getPriceDto, Room::setPrice)
                .addMapping(RoomDTO::getAvailableDto, Room::setAvailable);

        mapper.createTypeMap(Room.class, RoomDTO.class)
                .addMapping(Room::getNumber, RoomDTO::setNumberDto)
                .addMapping(Room::getType, RoomDTO::setTypeDto)
                .addMapping(Room::getPrice, RoomDTO::setPriceDto)
                .addMapping(Room::getAvailable, RoomDTO::setAvailableDto);

        return mapper;
    }

    @Bean
    public ModelMapper reservationMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.createTypeMap(Reservation.class, ReservationDTO.class)
                .addMapping(e -> e.getRoom().getNumber(),   (dest, v) -> dest.getRoom().setNumberDto((String) v))
                .addMapping(e -> e.getRoom().getType(),     (dest, v) -> dest.getRoom().setTypeDto((String) v))
                .addMapping(e -> e.getRoom().getPrice(),    (dest, v) -> dest.getRoom().setPriceDto((BigDecimal) v))
                .addMapping(e -> e.getRoom().getAvailable(),(dest, v) -> dest.getRoom().setAvailableDto((Boolean) v));

        return mapper;
    }

}
