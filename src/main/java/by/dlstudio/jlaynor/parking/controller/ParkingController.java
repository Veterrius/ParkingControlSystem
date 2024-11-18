package by.dlstudio.jlaynor.parking.controller;

import by.dlstudio.jlaynor.parking.model.domain.other.ParkingDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.file.AccessDeniedException;

public interface ParkingController {
    @GetMapping
    String showAllParkings(Model model) throws AccessDeniedException;

    @PutMapping("/update")
    ResponseEntity<Void> updateParking(@RequestBody ParkingDTO parkingDTO) throws AccessDeniedException;
}
