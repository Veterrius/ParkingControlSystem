package by.dlstudio.jlaynor.parking.model.service;

import by.dlstudio.jlaynor.parking.model.domain.entity.CreditCard;

import java.util.List;

public interface CreditCardsService {

    List<CreditCard> getCreditCards();

    Boolean addCreditCard(CreditCard creditCard);

    CreditCard refreshCreditCard(CreditCard creditCard);
}
