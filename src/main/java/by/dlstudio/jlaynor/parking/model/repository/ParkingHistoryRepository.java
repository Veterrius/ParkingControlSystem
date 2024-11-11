package by.dlstudio.jlaynor.parking.model.repository;

import by.dlstudio.jlaynor.parking.model.domain.entity.ParkingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingHistoryRepository extends JpaRepository<ParkingHistory, Integer> {
}
