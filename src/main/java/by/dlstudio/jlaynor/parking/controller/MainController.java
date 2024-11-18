package by.dlstudio.jlaynor.parking.controller;

import org.springframework.web.bind.annotation.GetMapping;

public interface MainController {
    @GetMapping("/")
    String showMainMenu();
}
