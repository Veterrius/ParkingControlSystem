package by.dlstudio.jlaynor.parking.model.service;

import by.dlstudio.jlaynor.parking.model.domain.entity.ParkingHistory;
import by.dlstudio.jlaynor.parking.model.domain.entity.User;
import by.dlstudio.jlaynor.parking.model.domain.other.CreditCardDTO;
import by.dlstudio.jlaynor.parking.model.domain.other.ReservationDTO;

public interface ReservationService {

    ParkingHistory applyReservation(ReservationDTO reservationDTO);

    User reserveParking(String creditCardNumber, User currentUser, ParkingHistory newParkingHistory);

    Boolean addCreditCard(CreditCardDTO creditCardDTO, User currentUser);
}
