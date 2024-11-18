package by.dlstudio.jlaynor.parking.controller;

import by.dlstudio.jlaynor.parking.model.domain.entity.ParkingHistory;
import by.dlstudio.jlaynor.parking.model.domain.other.CreditCardDTO;
import by.dlstudio.jlaynor.parking.model.domain.other.ReservationDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

public interface ReservationController {


    @GetMapping("{parkingId}")
    String showParkingReservation(@PathVariable Integer parkingId, Model model) throws AccessDeniedException;

    @GetMapping("/payment")
    String showCreditCards(Model model) throws AccessDeniedException;

    @PostMapping
    ResponseEntity<Void> applyReservation(@RequestBody ReservationDTO reservationDTO, HttpSession session) throws AccessDeniedException;

    @PostMapping("/payment")
    ResponseEntity<Void> addCreditCard(@RequestBody CreditCardDTO creditCardDTO) throws AccessDeniedException;

    @PutMapping
    ResponseEntity<Void> confirmReservation(@RequestBody CreditCardDTO creditCardDTO,
                                            @SessionAttribute ParkingHistory newParkingHistory) throws AccessDeniedException;
}
