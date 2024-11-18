package by.dlstudio.jlaynor.parking.controller.impl;

import by.dlstudio.jlaynor.parking.controller.MainController;
import by.dlstudio.jlaynor.parking.controller.abstr.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainControllerImpl extends AbstractController implements MainController {

    @Override
    @GetMapping("/")
    public String showMainMenu() {
        if (currentUser != null) {
            switch (currentUser.getRole()) {
                case USER -> {return "main-user";}
                case ADMIN -> {return "main-admin";}
            }
        }
        return "redirect:/auth/login";
    }
}
