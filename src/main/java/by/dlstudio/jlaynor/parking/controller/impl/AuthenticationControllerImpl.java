package by.dlstudio.jlaynor.parking.controller.impl;

import by.dlstudio.jlaynor.parking.controller.abstr.AbstractController;
import by.dlstudio.jlaynor.parking.model.domain.entity.User;
import by.dlstudio.jlaynor.parking.model.domain.enums.Role;
import by.dlstudio.jlaynor.parking.model.domain.other.LoginRequest;
import by.dlstudio.jlaynor.parking.model.domain.other.SignUpRequest;
import by.dlstudio.jlaynor.parking.model.service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthenticationControllerImpl extends AbstractController {
    private final AuthenticationService authService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("signUpRequest", new SignUpRequest());
        return "register";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (currentUser != null) {
            currentUser = null;
            session.setAttribute("currentUser", null);
        }
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest, HttpSession session) {
        User user = authService.login(loginRequest.getLoginEmail(), loginRequest.getPassword());
        if (user != null) {
            session.setAttribute("currentUser", user);
            setCurrentUser(user);
            return "redirect:/";
        }
        return "redirect:/auth/login?error";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute SignUpRequest signUpRequest) {
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setPassword(signUpRequest.getPassword());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(Role.USER);
        if (authService.register(user)) return "redirect:/auth/login";
        return "redirect: /auth/login?error";
    }

}
