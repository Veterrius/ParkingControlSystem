package by.dlstudio.jlaynor.parking.model.service.impl;

import by.dlstudio.jlaynor.parking.model.domain.entity.CreditCard;
import by.dlstudio.jlaynor.parking.model.domain.entity.User;
import by.dlstudio.jlaynor.parking.model.repository.CreditCardsRepository;
import by.dlstudio.jlaynor.parking.model.service.CreditCardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CreditCardsServiceImpl implements CreditCardsService {
    private final CreditCardsRepository creditCardsRepository;

    @Override
    public List<CreditCard> getCreditCards() {
        return creditCardsRepository.findAll();
    }

    @Override
    public Boolean addCreditCard(CreditCard creditCard) {
        CreditCard saved = creditCardsRepository.save(creditCard);
        return saved.getCardNumber() != null;
    }
}
