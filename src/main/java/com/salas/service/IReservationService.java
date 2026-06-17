package com.salas.service;

import com.salas.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;

public interface IReservationService extends ICRUD<Reservation, Integer> {

    Reservation saveWithValidations(Reservation reservation) throws Exception;
    List<Reservation> findConflictingReservations(Integer roomId, LocalDateTime checkIn, LocalDateTime checkOut);
    List<Reservation> searchByCustomerName(String customerName);
    List<Reservation> searchByCheckInDateRange(LocalDateTime startDate, LocalDateTime endDate);
}

