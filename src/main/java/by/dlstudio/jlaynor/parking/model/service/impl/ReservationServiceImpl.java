package by.dlstudio.jlaynor.parking.model.service.impl;

import by.dlstudio.jlaynor.parking.exception.ParkingException;
import by.dlstudio.jlaynor.parking.model.domain.entity.CreditCard;
import by.dlstudio.jlaynor.parking.model.domain.entity.Parking;
import by.dlstudio.jlaynor.parking.model.domain.entity.ParkingHistory;
import by.dlstudio.jlaynor.parking.model.domain.entity.User;
import by.dlstudio.jlaynor.parking.model.domain.other.CreditCardDTO;
import by.dlstudio.jlaynor.parking.model.domain.other.ReservationDTO;
import by.dlstudio.jlaynor.parking.model.service.CreditCardsService;
import by.dlstudio.jlaynor.parking.model.service.ParkingService;
import by.dlstudio.jlaynor.parking.model.service.PersonalInformationService;
import by.dlstudio.jlaynor.parking.model.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {
    private final CreditCardsService creditCardsService;
    private final ParkingService parkingService;
    private final PersonalInformationService personalInformationService;
    
    
    @Override
    public ParkingHistory applyReservation(ReservationDTO reservationDTO) {
        ParkingHistory parkingHistory = new ParkingHistory();
        parkingHistory.setParkingName(parkingService.getParkingById(reservationDTO.getParkingId()).orElseThrow(
                () -> new InvalidParameterException("Wrong parking")).getName()
        );
        parkingHistory.setReservedLots(reservationDTO.getNumberOfLots());
        parkingHistory.setDate(Date.valueOf(LocalDate.now()));
        parkingHistory.setFullHours(calculateFullHours(reservationDTO.getStartTime(), reservationDTO.getEndTime()));
        return parkingHistory;
    }
    

    @Override
    public User reserveParking(String creditCardNumber, User currentUser, ParkingHistory newParkingHistory) {
        CreditCard userCreditCard = currentUser.getCreditCards().stream()
                .filter(a -> a.getCardNumber().equals(creditCardNumber))
                .toList().get(0);
        userCreditCard = creditCardsService.refreshCreditCard(userCreditCard);

        Parking reservedParking = parkingService.getParkingByName(newParkingHistory.getParkingName())
                .orElseThrow(() -> new ParkingException("Invalid Parking"));

        Float parkingPrice = reservedParking.getPrice()
                * newParkingHistory.getReservedLots()
                * newParkingHistory.getFullHours();

        if (isValidForParking(userCreditCard.getBalance(), reservedParking, parkingPrice, newParkingHistory.getReservedLots())) {
            userCreditCard.setBalance(userCreditCard.getBalance() - parkingPrice);
            creditCardsService.addCreditCard(userCreditCard);
            newParkingHistory = parkingService.saveParkingHistory(newParkingHistory);
            currentUser.getParkingHistories().add(newParkingHistory);
            personalInformationService.updateUser(currentUser);
            reservedParking.setFreeNumberOfSpace(reservedParking.getFreeNumberOfSpace() - newParkingHistory.getReservedLots());
            parkingService.updateParking(reservedParking);
            return currentUser;
        }
        return currentUser;
    }

    @Override
    public Boolean addCreditCard(CreditCardDTO creditCardDTO, User currentUser) {
        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber(creditCardDTO.getCardNumber());
        creditCard.setCvc(creditCardDTO.getCvc());
        creditCard.setCardDate(creditCardDTO.getExpirationDate());
        if (creditCardsService.addCreditCard(creditCard)) {
            currentUser.getCreditCards().add(creditCard);
            return personalInformationService.updateUser(currentUser);
        }
        return false;
    }

    private Boolean isValidForParking(Float userBalance, Parking reservedParking, Float parkingPrice, Integer reservedLots) {
        return userBalance >= parkingPrice
                && reservedParking.getFreeNumberOfSpace() >= reservedLots;
    }

    private Integer calculateFullHours(String startTime, String endTime) {
        LocalTime start = LocalTime.of(Integer.parseInt(startTime.substring(0, startTime.indexOf(":"))),
                Integer.parseInt(startTime.substring(startTime.indexOf(":")+1)));
        LocalTime end = LocalTime.of(Integer.parseInt(endTime.substring(0, endTime.indexOf(":"))),
                Integer.parseInt(endTime.substring(endTime.indexOf(":")+1)));
        int hours = (int) Math.floor((double) (end.toSecondOfDay() - start.toSecondOfDay()) / 3600);
        return Math.max(hours, 1);
    }
}
