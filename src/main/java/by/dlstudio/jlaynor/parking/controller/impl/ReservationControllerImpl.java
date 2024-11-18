package by.dlstudio.jlaynor.parking.controller.impl;

import by.dlstudio.jlaynor.parking.controller.ReservationController;
import by.dlstudio.jlaynor.parking.controller.abstr.AbstractController;
import by.dlstudio.jlaynor.parking.model.domain.entity.Parking;
import by.dlstudio.jlaynor.parking.model.domain.entity.ParkingHistory;
import by.dlstudio.jlaynor.parking.model.domain.other.CreditCardDTO;
import by.dlstudio.jlaynor.parking.model.domain.other.ReservationDTO;
import by.dlstudio.jlaynor.parking.model.service.ParkingService;
import by.dlstudio.jlaynor.parking.model.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.security.InvalidParameterException;

@AllArgsConstructor
@Controller
@RequestMapping("/reservation")
public class ReservationControllerImpl extends AbstractController implements ReservationController {
    private final ParkingService parkingService;
    private final ReservationService reservationService;

    @Override
    @GetMapping("{parkingId}")
    public String showParkingReservation(@PathVariable Integer parkingId, Model model) throws AccessDeniedException {
        requireAuth();
        Parking reservedParking = parkingService.getParkingById(parkingId).orElseThrow(
                () -> new InvalidParameterException("Wrong parking selected"));
        model.addAttribute("parking", reservedParking);
        return "reservation";
    }

    @Override
    @GetMapping("/payment")
    public String showCreditCards(Model model) throws AccessDeniedException {
        requireAuth();
        model.addAttribute("cards", currentUser.getCreditCards());
        return "credit-cards";
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> applyReservation(@RequestBody ReservationDTO reservationDTO, HttpSession session) throws AccessDeniedException {
        requireAuth();
        ParkingHistory newParkingHistory = reservationService.applyReservation(reservationDTO);
        session.setAttribute("newParkingHistory", newParkingHistory);
        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping("/payment")
    public ResponseEntity<Void> addCreditCard(@RequestBody CreditCardDTO creditCardDTO) throws AccessDeniedException {
        requireAuth();
        if (reservationService.addCreditCard(creditCardDTO, currentUser)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    @PutMapping
    public ResponseEntity<Void> confirmReservation(@RequestBody CreditCardDTO creditCardDTO,
                                                   @SessionAttribute ParkingHistory newParkingHistory) throws AccessDeniedException {
        requireAuth();
        int parkings_before_reservation = currentUser.getParkingHistories().size();
        if (parkings_before_reservation < reservationService.reserveParking(
                creditCardDTO.getCardNumber(),
                currentUser,
                newParkingHistory
        ).getParkingHistories().size()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
