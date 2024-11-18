package by.dlstudio.jlaynor.parking.controller;

import by.dlstudio.jlaynor.parking.model.domain.entity.ParkingHistory;
import by.dlstudio.jlaynor.parking.model.domain.other.ReportDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface AnalyticsController {
    @GetMapping
    String showAnalytics(Model model) throws AccessDeniedException;

    @PostMapping
    ResponseEntity<Void> getReport(@RequestBody ReportDTO reportDTO, Model model) throws AccessDeniedException;

    @RequestMapping("/{parkingId}/history")
    ResponseEntity<List<ParkingHistory>> getParkingHistories(@PathVariable Integer parkingId) throws AccessDeniedException;
}
