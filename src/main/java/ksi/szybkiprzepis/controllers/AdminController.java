package ksi.szybkiprzepis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import ksi.szybkiprzepis.models.*;
import ksi.szybkiprzepis.services.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

   @Autowired
   private UserService userService;
   
   @Autowired
   private RecipeService recipeService;
   
   @Autowired
   private CommentService commentService;

   private boolean isAdmin(HttpSession session) {
       User user = (User) session.getAttribute("user");
       return user != null && "ADMIN".equals(user.getRole());
   }

   @GetMapping("")
   public String adminPanel(HttpSession session, Model model) {
       if (!isAdmin(session)) {
           return "redirect:/login";
       }
       
       List<User> users = userService.findAll();
       List<Recipe> recipes = recipeService.findAll();
       List<Comment> comments = commentService.findAll();
       
       model.addAttribute("totalUsers", users.size());
       model.addAttribute("totalRecipes", recipes.size());
       model.addAttribute("totalComments", comments.size());
       
       return "admin/dashboard";
   }

   @GetMapping("/recipes")
   public String manageRecipes(HttpSession session, Model model) {
       if (!isAdmin(session)) {
           return "redirect:/login";
       }
       
       model.addAttribute("recipes", recipeService.findAll());
       return "admin/recipes";
   }

   @PostMapping("/recipe/delete/{id}")
   public String deleteRecipe(@PathVariable("id") Long id, HttpSession session) {
       if (!isAdmin(session)) {
           return "redirect:/login";
       }
       
       recipeService.deleteById(id);
       return "redirect:/admin/recipes";
   }

   @GetMapping("/comments")
   public String manageComments(HttpSession session, Model model) {
       if (!isAdmin(session)) {
           return "redirect:/login";
       }
       
       model.addAttribute("comments", commentService.findAll());
       return "admin/comments";
   }

   @PostMapping("/comment/delete/{id}")
   public String deleteComment(@PathVariable("id") Long id, HttpSession session) {
       if (!isAdmin(session)) {
           return "redirect:/login";
       }
       
       commentService.deleteById(id);
       return "redirect:/admin/comments";
   }

   @GetMapping("/users")
   public String manageUsers(HttpSession session, Model model) {
       if (!isAdmin(session)) {
           return "redirect:/login";
       }
       List<User> userList = userService.findAll();
       model.addAttribute("userList", userList);
       return "admin/users";  
   }
}
