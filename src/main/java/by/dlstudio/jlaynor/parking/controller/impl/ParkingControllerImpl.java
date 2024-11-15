package by.dlstudio.jlaynor.parking.controller.impl;

import by.dlstudio.jlaynor.parking.controller.abstr.AbstractController;
import by.dlstudio.jlaynor.parking.model.domain.entity.Parking;
import by.dlstudio.jlaynor.parking.model.domain.entity.Rule;
import by.dlstudio.jlaynor.parking.model.domain.enums.Role;
import by.dlstudio.jlaynor.parking.model.domain.other.ParkingDTO;
import by.dlstudio.jlaynor.parking.model.service.ParkingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.security.InvalidParameterException;
import java.util.LinkedHashMap;

@AllArgsConstructor
@Controller
@RequestMapping("/parking")
public class ParkingControllerImpl extends AbstractController {
    private ParkingService parkingService;

    @GetMapping
    public String showAllParkings(Model model) throws AccessDeniedException {
        requireAuth();
        model.addAttribute("parkings", parkingService.getListOfParkings());
        return currentUser.getRole().equals(Role.ADMIN) ? "parking-update" : "parking";
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateParking(@RequestBody ParkingDTO parkingDTO) throws AccessDeniedException {
        requireRole(Role.ADMIN);
        Parking updatedParking = parkingService.getParkingById(parkingDTO.getParkingId()).orElseThrow(() ->
                new InvalidParameterException("Wrong parking")
        );
        for (var characteristic : parkingDTO.getCharacteristics()) {
            switch (characteristic.get("type")) {
                case "PRICE":
                    updatedParking.setPrice(Float.valueOf(characteristic.get("value")));
                case "WORKING_TIME":
                    updatedParking.setWorkingTime(characteristic.get("value"));
            }
        }
        if (parkingService.updateParking(updatedParking)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
