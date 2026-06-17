package com.salas.repo;

import com.salas.model.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IReservationRepo extends IGenericRepo<Reservation, Integer> {

    @Query("FROM Reservation r WHERE r.room.idRoom = :roomId " +
            "AND ((r.checkInDate < :checkOut AND r.checkOutDate > :checkIn))")
    List<Reservation> findConflictingReservations(
            @Param("roomId") Integer roomId,
            @Param("checkIn") LocalDateTime checkIn,
            @Param("checkOut") LocalDateTime checkOut
    );

    @Query("FROM Reservation r WHERE LOWER(r.customerName) LIKE %:customerName%")
    List<Reservation> searchByCustomerName(@Param("customerName") String customerName);

    @Query("FROM Reservation r WHERE r.checkInDate BETWEEN :startDate AND :endDate")
    List<Reservation> searchByCheckInDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
