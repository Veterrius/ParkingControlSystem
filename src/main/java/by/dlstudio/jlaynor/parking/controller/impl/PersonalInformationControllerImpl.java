package by.dlstudio.jlaynor.parking.controller.impl;

import by.dlstudio.jlaynor.parking.controller.abstr.AbstractController;
import by.dlstudio.jlaynor.parking.model.domain.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.file.AccessDeniedException;

@Controller
@RequestMapping("/info")
public class PersonalInformationControllerImpl extends AbstractController {

    @GetMapping
    public String showPersonalInfo(Model model) throws AccessDeniedException {
        requireAuth();
        model.addAttribute("user", currentUser);
        return "personal-info";
    }
}
