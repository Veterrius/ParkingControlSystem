package by.dlstudio.jlaynor.parking.model.service.impl;

import by.dlstudio.jlaynor.parking.model.domain.other.Report;
import by.dlstudio.jlaynor.parking.model.domain.entity.Parking;
import by.dlstudio.jlaynor.parking.model.service.AnalyticsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.StringJoiner;

@AllArgsConstructor
@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    @Override
    public Report getReport(Parking parking) {
        Report resultReport = new Report();
        resultReport.setTitle(this.formReportTitle(parking));
        resultReport.setBody(this.formReportBody(parking));
        return resultReport;
    }

    @Override
    public Boolean sendByEmail(Report report) {
        // TODO Send by email
        return null;
    }

    private String formReportTitle(Parking parking) {
        return new StringJoiner("|")
                .add(parking.getName())
                .add(parking.getLocation())
                .toString();
    }

    private String formReportBody(Parking parking) {
        return "This parking is currently administered by " +
                parking.getListOfAdministrators().size() + " admins" +
                "\nThere are " + parking.getFreeNumberOfSpace() +
                " free parking spots out of " + parking.getTotalNumberOfSpace();
    }
}
