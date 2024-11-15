package by.dlstudio.jlaynor.parking.model.service;

import by.dlstudio.jlaynor.parking.model.domain.entity.Parking;
import by.dlstudio.jlaynor.parking.model.domain.entity.ParkingHistory;

import java.util.List;
import java.util.Optional;

public interface ParkingService {

    List<Parking> getListOfParkings();

    Boolean updateParking(Parking parking);

    Optional<Parking> getParkingById(Integer parkingId);

    ParkingHistory saveParkingHistory(ParkingHistory parkingHistory);
}
