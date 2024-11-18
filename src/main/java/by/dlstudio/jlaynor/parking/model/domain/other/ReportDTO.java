package by.dlstudio.jlaynor.parking.model.domain.other;

import lombok.Data;

import java.util.List;

@Data
public class ReportDTO {
    private Integer parkingId;
    private String name;
    private String address;
    private String lots;
    private List<String> history;
}