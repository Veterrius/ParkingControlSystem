package by.dlstudio.jlaynor.parking.model.domain.other;

import by.dlstudio.jlaynor.parking.model.domain.entity.Rule;
import lombok.Data;

import java.util.List;

@Data
public class RuleDTO {
    private List<Integer> ids;
    private Integer id;
    private String title;
    private String body;
}
