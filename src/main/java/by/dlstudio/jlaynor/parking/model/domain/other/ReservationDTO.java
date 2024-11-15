package by.dlstudio.jlaynor.parking.model.domain.other;

import lombok.Data;

@Data
public class ReservationDTO {
    private Integer parkingId;
    private Integer numberOfLots;
    private String startTime;
    private String endTime;
}
