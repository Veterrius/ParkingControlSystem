package by.dlstudio.jlaynor.parking.model.repository;

import by.dlstudio.jlaynor.parking.model.domain.entity.ParkingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingHistoryRepository extends JpaRepository<ParkingHistory, Integer> {

    List<ParkingHistory> findParkingHistoriesByParkingName(String parkingName);
}
