package by.dlstudio.jlaynor.parking.model.service.impl;

import by.dlstudio.jlaynor.parking.exception.ParkingException;
import by.dlstudio.jlaynor.parking.model.domain.entity.User;
import by.dlstudio.jlaynor.parking.model.domain.other.Report;
import by.dlstudio.jlaynor.parking.model.domain.entity.Parking;
import by.dlstudio.jlaynor.parking.model.domain.other.ReportDTO;
import by.dlstudio.jlaynor.parking.model.service.AnalyticsService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.StringJoiner;

@AllArgsConstructor
@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    @Override
    public Report getReport(Parking parking, ReportDTO reportDTO) {
        Report resultReport = new Report();
        resultReport.setTitle(this.formReportTitle(reportDTO));
        resultReport.setBody(this.formReportBody(parking, reportDTO));
        return resultReport;
    }

    @Override
    public Boolean sendByEmail(Report report, User currentUser) {
        String username = "avrukievich@gmail.com";
        String password = "lbga sqlm nqng xscs";
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("avrukievich@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(currentUser.getEmail())
            );
            message.setSubject(report.getTitle());
            message.setText(report.getBody());

            Transport.send(message);

            return true;

        } catch (MessagingException e) {
            throw new ParkingException("Wrong mail");
        }
    }

    private String formReportTitle(ReportDTO reportDTO) {
        return new StringJoiner(" | ")
                .add(reportDTO.getName())
                .add(reportDTO.getAddress())
                .add("Report")
                .toString();
    }

    private String formReportBody(Parking parking, ReportDTO reportDTO) {
        return "This parking is currently administered by "
                + parking.getListOfAdministrators().size() + " admins"
                + (reportDTO.getLots() != null ? ("\nFree Lots: " + reportDTO.getLots()) : "")
                + (reportDTO.getHistory() != null ?
                ("\nHistory:" + reportDTO.getHistory().stream().map(a -> "\n" + a).toList()) : "");
    }
}
