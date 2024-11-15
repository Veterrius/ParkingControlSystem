package by.dlstudio.jlaynor.parking.model.domain.other;

import lombok.Data;

@Data
public class SignUpRequest {
    private String name;
    private String email;
    private String password;
}
