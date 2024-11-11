package by.dlstudio.jlaynor.parking.model.repository;

import by.dlstudio.jlaynor.parking.model.domain.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Parking, Integer> {

}
