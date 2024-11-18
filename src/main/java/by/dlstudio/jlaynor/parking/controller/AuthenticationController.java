package by.dlstudio.jlaynor.parking.controller;

import by.dlstudio.jlaynor.parking.model.domain.other.LoginRequest;
import by.dlstudio.jlaynor.parking.model.domain.other.SignUpRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

public interface AuthenticationController {
    @GetMapping("/login")
    String showLoginForm(Model model);

    @GetMapping("/signup")
    String showSignUpForm(Model model);

    @GetMapping("/logout")
    String logout(HttpSession session);

    @PostMapping("/login")
    String login(@ModelAttribute LoginRequest loginRequest, HttpSession session);

    @PostMapping("/signup")
    String signUp(@ModelAttribute SignUpRequest signUpRequest);
}
