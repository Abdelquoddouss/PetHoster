package com.java.pethoster.web.rest;

import com.java.pethoster.service.ReservationService;
import com.java.pethoster.web.vm.request.ReservationRequest;
import com.java.pethoster.web.vm.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        ReservationResponse reservationResponse = reservationService.createReservation(reservationRequest);
        return ResponseEntity.ok(reservationResponse);
    }

    @PutMapping("/{id}/annuler")
    public ResponseEntity<ReservationResponse> annulerReservation(@PathVariable UUID id) {
        ReservationResponse reservationResponse = reservationService.annulerReservation(id);
        return ResponseEntity.ok(reservationResponse);
    }
}