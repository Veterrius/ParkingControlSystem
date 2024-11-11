package by.dlstudio.jlaynor.parking.model.service;

import by.dlstudio.jlaynor.parking.model.domain.other.Report;
import by.dlstudio.jlaynor.parking.model.domain.entity.Parking;

public interface AnalyticsService {

    Report getReport(Parking parking);

    Boolean sendByEmail(Report report);
}
