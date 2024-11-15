package by.dlstudio.jlaynor.parking.model.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "credit_cards")
public class CreditCard {

    @Id
    @Column(name = "card_number", nullable = false, unique = true)
    private String cardNumber;

    @Column(name = "cvc", nullable = false)
    private Integer cvc;

    @Column(name = "card_date", nullable = false)
    private String cardDate;

    @Column(name = "balance")
    private Float balance;

    public String getLastFourDigits() {
        return cardNumber.substring(cardNumber.length() - 4);
    }
}
