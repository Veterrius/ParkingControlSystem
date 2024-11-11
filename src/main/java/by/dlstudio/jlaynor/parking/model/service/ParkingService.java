package by.dlstudio.jlaynor.parking.model.service;

import by.dlstudio.jlaynor.parking.model.domain.entity.Parking;

import java.util.List;

public interface ParkingService {

    List<Parking> getListOfParkings();

    Boolean updateParking(Parking parking);
}
