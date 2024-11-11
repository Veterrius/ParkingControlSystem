package by.dlstudio.jlaynor.parking.model.service.impl;

import by.dlstudio.jlaynor.parking.model.domain.entity.Parking;
import by.dlstudio.jlaynor.parking.model.repository.ParkingRepository;
import by.dlstudio.jlaynor.parking.model.service.ParkingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ParkingServiceImpl implements ParkingService {
    private final ParkingRepository parkingRepository;

    @Override
    public List<Parking> getListOfParkings() {
        return parkingRepository.findAll();
    }

    @Override
    public Boolean updateParking(Parking parking) {
        return parkingRepository.save(parking).getId() != null;
    }
}
