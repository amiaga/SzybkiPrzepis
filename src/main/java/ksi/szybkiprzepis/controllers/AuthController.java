package ksi.szybkiprzepis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;
import ksi.szybkiprzepis.models.User;
import ksi.szybkiprzepis.services.UserService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, HttpSession session, Model model) {
        if (userService.authenticate(user.getUsername(), user.getPassword())) {
            User authenticatedUser = userService.findByUsername(user.getUsername());
            session.setAttribute("user", authenticatedUser);
            return "redirect:/";
        }
        model.addAttribute("error", "Nieprawidłowa nazwa użytkownika lub hasło");
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        try {
            if (userService.findByUsername(user.getUsername()) != null) {
                model.addAttribute("error", "Nazwa użytkownika jest już zajęta");
                return "register";
            }
            if (userService.findByEmail(user.getEmail()) != null) {
                model.addAttribute("error", "Email jest już używany");
                return "register";
            }
            
            user.setRole("USER");
            userService.save(user);
            return "redirect:/login?registered";
        } catch (Exception e) {
            model.addAttribute("error", "Wystąpił błąd podczas rejestracji");
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/?logout";
    }

    @ModelAttribute("isAuthenticated")
    public boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("user") != null;
    }
}