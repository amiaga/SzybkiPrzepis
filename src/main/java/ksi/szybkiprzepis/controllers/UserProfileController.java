package ksi.szybkiprzepis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.servlet.http.HttpSession;
import ksi.szybkiprzepis.models.User;
import ksi.szybkiprzepis.models.Recipe;
import ksi.szybkiprzepis.services.UserService;
import ksi.szybkiprzepis.services.RecipeService;
import java.util.List;

@Controller
public class UserProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        user = userService.findByUsername(user.getUsername());
        List<Recipe> userRecipes = user.getRecipes();
        
        model.addAttribute("user", user);
        model.addAttribute("userRecipes", userRecipes);
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String showEditProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("user", user);
        return "edit_profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User updatedUser, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            
            currentUser.setEmail(updatedUser.getEmail());
            if (!updatedUser.getPassword().isEmpty()) {
                currentUser.setPassword(updatedUser.getPassword());
            }
            
            userService.save(currentUser);
            session.setAttribute("user", currentUser);
            model.addAttribute("success", "Profil został zaktualizowany");
            return "redirect:/profile?updated";
        } catch (Exception e) {
            model.addAttribute("error", "Wystąpił błąd podczas aktualizacji profilu");
            return "edit_profile";
        }
    }
}