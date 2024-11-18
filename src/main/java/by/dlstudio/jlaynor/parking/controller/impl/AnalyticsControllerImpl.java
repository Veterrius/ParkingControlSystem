package by.dlstudio.jlaynor.parking.controller.impl;

import by.dlstudio.jlaynor.parking.controller.AnalyticsController;
import by.dlstudio.jlaynor.parking.controller.abstr.AbstractController;
import by.dlstudio.jlaynor.parking.exception.ParkingException;
import by.dlstudio.jlaynor.parking.model.domain.entity.ParkingHistory;
import by.dlstudio.jlaynor.parking.model.domain.enums.Role;
import by.dlstudio.jlaynor.parking.model.domain.other.Report;
import by.dlstudio.jlaynor.parking.model.domain.other.ReportDTO;
import by.dlstudio.jlaynor.parking.model.service.AnalyticsService;
import by.dlstudio.jlaynor.parking.model.service.ParkingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/analytics")
public class AnalyticsControllerImpl extends AbstractController implements AnalyticsController {
    private final AnalyticsService analyticsService;
    private final ParkingService parkingService;

    @Override
    @GetMapping
    public String showAnalytics(Model model) throws AccessDeniedException {
        requireRole(Role.ADMIN);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("parkings", parkingService.getListOfParkings());
        return "analytics";
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> getReport(@RequestBody ReportDTO reportDTO, Model model) throws AccessDeniedException {
        requireRole(Role.ADMIN);
        Report report = analyticsService.getReport(parkingService.getParkingById(reportDTO.getParkingId())
                .orElseThrow(() -> new ParkingException("Invalid Parking")), reportDTO);
        analyticsService.sendByEmail(report, currentUser);
        return ResponseEntity.ok().build();
    }

    @Override
    @RequestMapping("/{parkingId}/history")
    public ResponseEntity<List<ParkingHistory>> getParkingHistories(@PathVariable Integer parkingId) throws AccessDeniedException {
        requireRole(Role.ADMIN);
        var histories = parkingService.getParkingHistories(parkingService.getParkingById(parkingId)
                .orElseThrow(() -> new ParkingException("Invalid parking")));
        return ResponseEntity.ok(histories);
    }
}
