package by.dlstudio.jlaynor.parking.model.domain.other;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
public class ParkingDTO {
    private Integer parkingId;
    private List<LinkedHashMap<String, String>> characteristics;
}
