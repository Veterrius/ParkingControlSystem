package by.dlstudio.jlaynor.parking.controller.impl;

import by.dlstudio.jlaynor.parking.controller.abstr.AbstractController;
import by.dlstudio.jlaynor.parking.model.domain.entity.CreditCard;
import by.dlstudio.jlaynor.parking.model.domain.entity.Parking;
import by.dlstudio.jlaynor.parking.model.domain.entity.ParkingHistory;
import by.dlstudio.jlaynor.parking.model.domain.other.CreditCardDTO;
import by.dlstudio.jlaynor.parking.model.domain.other.ReservationDTO;
import by.dlstudio.jlaynor.parking.model.service.CreditCardsService;
import by.dlstudio.jlaynor.parking.model.service.ParkingService;
import by.dlstudio.jlaynor.parking.model.service.PersonalInformationService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.security.InvalidParameterException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
@RequestMapping("/reservation")
public class ReservationControllerImpl extends AbstractController {
    private final CreditCardsService creditCardsService;
    private final PersonalInformationService personalInformationService;
    private final ParkingService parkingService;

    @GetMapping("{parkingId}")
    public String showParkingReservation(@PathVariable Integer parkingId, Model model, HttpSession session) throws AccessDeniedException {
        requireAuth();
        Parking reservedParking = parkingService.getParkingById(parkingId).orElseThrow(
                () -> new InvalidParameterException("Wrong parking selected"));
        model.addAttribute("parking", reservedParking);
        session.setAttribute("parking", reservedParking);
        return "reservation";
    }

    @GetMapping("/payment")
    public String showCreditCards(Model model) throws AccessDeniedException {
        requireAuth();
        model.addAttribute("cards", currentUser.getCreditCards());
        return "credit-cards";
    }

    @PostMapping
    public ResponseEntity<Void> applyReservation(@RequestBody ReservationDTO reservationDTO, HttpSession session) throws AccessDeniedException {
        requireAuth();
        ParkingHistory parkingHistory = new ParkingHistory();
        parkingHistory.setParkingName(parkingService.getParkingById(reservationDTO.getParkingId()).orElseThrow(
                () -> new InvalidParameterException("Wrong parking")).getName()
        );
        parkingHistory.setDate(Date.valueOf(LocalDate.now()));
        session.setAttribute("newParkingHistory", parkingHistory);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/payment")
    public ResponseEntity<Void> addCreditCard(@RequestBody CreditCardDTO creditCardDTO) throws AccessDeniedException {
        requireAuth();
        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber(creditCardDTO.getCardNumber());
        creditCard.setCvc(creditCardDTO.getCvc());
        creditCard.setCardDate(creditCardDTO.getExpirationDate());
        if (creditCardsService.addCreditCard(creditCard)) {
            currentUser.getCreditCards().add(creditCard);
            personalInformationService.updateUser(currentUser);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping
    public ResponseEntity<Void> confirmReservation(@RequestBody CreditCardDTO creditCardDTO,
                                                   @SessionAttribute ParkingHistory newParkingHistory,
                                                   @SessionAttribute Parking parking) throws AccessDeniedException {
        requireAuth();
        CreditCard userCreditCard = currentUser.getCreditCards().stream()
                        .filter(a -> a.getCardNumber().equals(creditCardDTO.getCardNumber()))
                        .toList().get(0);
        if (userCreditCard.getBalance() >= Objects.requireNonNull(parking).getPrice()) {
            userCreditCard.setBalance(userCreditCard.getBalance() - parking.getPrice());
            creditCardsService.addCreditCard(userCreditCard);
            newParkingHistory = parkingService.saveParkingHistory(newParkingHistory);
            currentUser.getParkingHistories().add(newParkingHistory);
            personalInformationService.updateUser(currentUser);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
