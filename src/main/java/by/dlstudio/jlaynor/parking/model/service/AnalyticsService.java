package by.dlstudio.jlaynor.parking.model.service;

import by.dlstudio.jlaynor.parking.model.domain.entity.User;
import by.dlstudio.jlaynor.parking.model.domain.other.Report;
import by.dlstudio.jlaynor.parking.model.domain.entity.Parking;
import by.dlstudio.jlaynor.parking.model.domain.other.ReportDTO;

public interface AnalyticsService {

    Report getReport(Parking parking, ReportDTO reportDTO);

    Boolean sendByEmail(Report report, User currentUser);
}
