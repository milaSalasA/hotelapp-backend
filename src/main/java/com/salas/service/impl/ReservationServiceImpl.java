package com.salas.service.impl;

import com.salas.exception.ModelNotFoundException;
import com.salas.model.Reservation;
import com.salas.model.Room;
import com.salas.repo.IGenericRepo;
import com.salas.repo.IReservationRepo;
import com.salas.repo.IRoomRepo;
import com.salas.service.IReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl extends CRUDImpl<Reservation, Integer> implements IReservationService {

    private final IReservationRepo repo;
    private final IRoomRepo roomRepo;

    @Override
    protected IGenericRepo<Reservation, Integer> getRepo() {
        return repo;
    }

    @Transactional
    @Override
    public Reservation saveWithValidations(Reservation reservation) throws Exception {
        // Validación 1: Verificar que la habitación existe
        Room room = roomRepo.findById(reservation.getRoom().getIdRoom())
                .orElseThrow(() -> new ModelNotFoundException("Habitación no encontrada"));

        // Validación 2: Verificar que la habitación esté disponible
        if (!room.getAvailable()) {
            throw new IllegalArgumentException("La habitación no está disponible");
        }

        // Validación 3: Verificar que no haya conflictos de fechas con otras reservas
        List<Reservation> conflicts = repo.findConflictingReservations(
                reservation.getRoom().getIdRoom(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate()
        );

        if (!conflicts.isEmpty()) {
            throw new IllegalArgumentException("Ya existe una reserva para esta habitación en las fechas seleccionadas");
        }

        // Validación 4: La fecha de salida no puede ser anterior o igual a la de entrada
        if (!reservation.getCheckOutDate().isAfter(reservation.getCheckInDate())) {
            throw new IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada");
        }

        // Validación 5: Actualizar disponibilidad de la habitación automáticamente
        room.setAvailable(false);
        roomRepo.save(room);

        return repo.save(reservation);
    }

    @Override
    public List<Reservation> findConflictingReservations(Integer roomId, LocalDateTime checkIn, LocalDateTime checkOut) {
        return repo.findConflictingReservations(roomId, checkIn, checkOut);
    }

    @Override
    public List<Reservation> searchByCustomerName(String customerName) {
        return repo.searchByCustomerName(customerName.toLowerCase());
    }

    @Override
    public List<Reservation> searchByCheckInDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return repo.searchByCheckInDateRange(startDate, endDate);
    }
}

