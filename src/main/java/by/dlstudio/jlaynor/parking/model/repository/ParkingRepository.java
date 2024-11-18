package by.dlstudio.jlaynor.parking.model.repository;

import by.dlstudio.jlaynor.parking.model.domain.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingRepository extends JpaRepository<Parking, Integer> {

    Optional<Parking> findParkingByName(String name);
}
