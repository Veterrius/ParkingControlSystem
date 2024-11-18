package by.dlstudio.jlaynor.parking.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.file.AccessDeniedException;

public interface PersonalInformationController {
    @GetMapping
    String showPersonalInfo(Model model) throws AccessDeniedException;
}
