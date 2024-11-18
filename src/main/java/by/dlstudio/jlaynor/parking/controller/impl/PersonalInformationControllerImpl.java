package by.dlstudio.jlaynor.parking.controller.impl;

import by.dlstudio.jlaynor.parking.controller.PersonalInformationController;
import by.dlstudio.jlaynor.parking.controller.abstr.AbstractController;
import by.dlstudio.jlaynor.parking.model.service.CreditCardsService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.file.AccessDeniedException;

@AllArgsConstructor
@Controller
@RequestMapping("/info")
public class PersonalInformationControllerImpl extends AbstractController implements PersonalInformationController {
    private final CreditCardsService creditCardsService;

    @Override
    @GetMapping
    public String showPersonalInfo(Model model) throws AccessDeniedException {
        requireAuth();
        currentUser.getCreditCards().forEach(creditCardsService::refreshCreditCard);
        model.addAttribute("user", currentUser);
        return "personal-info";
    }
}
