package by.dlstudio.jlaynor.parking.model.service.impl;

import by.dlstudio.jlaynor.parking.model.domain.entity.Parking;
import by.dlstudio.jlaynor.parking.model.domain.entity.ParkingHistory;
import by.dlstudio.jlaynor.parking.model.repository.ParkingHistoryRepository;
import by.dlstudio.jlaynor.parking.model.repository.ParkingRepository;
import by.dlstudio.jlaynor.parking.model.service.ParkingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ParkingServiceImpl implements ParkingService {
    private final ParkingRepository parkingRepository;
    private final ParkingHistoryRepository parkingHistoryRepository;

    @Override
    public List<Parking> getListOfParkings() {
        return parkingRepository.findAll();
    }

    @Override
    public Boolean updateParking(Parking parking) {
        return parkingRepository.save(parking).getId() != null;
    }

    @Override
    public Optional<Parking> getParkingById(Integer parkingId) {
        return parkingRepository.findById(parkingId);
    }

    @Override
    public ParkingHistory saveParkingHistory(ParkingHistory parkingHistory) {
        return parkingHistoryRepository.save(parkingHistory);
    }

    @Override
    public Optional<Parking> getParkingByName(String parkingName) {
        return parkingRepository.findParkingByName(parkingName);
    }

    @Override
    public List<ParkingHistory> getParkingHistories(Parking parking) {
        return parkingHistoryRepository.findParkingHistoriesByParkingName(parking.getName());
    }
}
