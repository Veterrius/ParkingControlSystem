package by.dlstudio.jlaynor.parking.model.domain.other;

import lombok.Data;

@Data
public class CreditCardDTO {
    private String cardNumber;
    private Integer cvc;
    private String expirationDate;
}
