package com.salas.controller;

import com.salas.dto.ReservationDTO;
import com.salas.model.Reservation;
import com.salas.service.IReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService service;

    @Qualifier("reservationMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> findAll() throws Exception {
        List<ReservationDTO> list = service.findAll().stream().map(this::convertToDTO).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> findById(@PathVariable Integer id) throws Exception {
        Reservation obj = service.findById(id);
        return ResponseEntity.ok(convertToDTO(obj));
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> save(@Valid @RequestBody ReservationDTO dto) throws Exception {
        Reservation obj = convertToEntity(dto);

        Reservation objResponse = service.saveWithValidations(obj);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(objResponse.getIdReservation())
                .toUri();

        return ResponseEntity.created(location).body(convertToDTO(objResponse));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> update(@PathVariable Integer id, @Valid @RequestBody ReservationDTO dto) throws Exception {
        dto.setIdReservation(id);
        Reservation obj = service.update(id, convertToEntity(dto));
        return ResponseEntity.ok(convertToDTO(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/customer")
    public ResponseEntity<List<ReservationDTO>> searchByCustomer(@RequestParam String name) {
        List<ReservationDTO> list = service.searchByCustomerName(name).stream()
                .map(this::convertToDTO)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/search/dates")
    public ResponseEntity<List<ReservationDTO>> searchByDates(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        List<ReservationDTO> list = service.searchByCheckInDateRange(
                java.time.LocalDateTime.parse(startDate),
                java.time.LocalDateTime.parse(endDate)
        ).stream()
                .map(this::convertToDTO)
                .toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/check-conflicts")
    public ResponseEntity<List<ReservationDTO>> checkConflicts(
            @RequestParam Integer roomId,
            @RequestParam String checkIn,
            @RequestParam String checkOut) {
        List<ReservationDTO> conflicts = service.findConflictingReservations(
                roomId,
                java.time.LocalDateTime.parse(checkIn),
                java.time.LocalDateTime.parse(checkOut)
        ).stream()
                .map(this::convertToDTO)
                .toList();

        return ResponseEntity.ok(conflicts);
    }

    private Reservation convertToEntity(ReservationDTO dto) {
        return modelMapper.map(dto, Reservation.class);
    }

    private ReservationDTO convertToDTO(Reservation obj) {
        return modelMapper.map(obj, ReservationDTO.class);
    }
}

