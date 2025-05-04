package ksi.szybkiprzepis.controllers;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import ksi.szybkiprzepis.models.Comment;
import ksi.szybkiprzepis.models.Recipe;
import ksi.szybkiprzepis.models.User;
import ksi.szybkiprzepis.services.CommentService;
import ksi.szybkiprzepis.services.RecipeService;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private RecipeService recipeService;

    @PostMapping("/save_comment")
    public String saveComment(
            @RequestParam("recipeId") Long recipeId,
            @RequestParam("content") String content,
            @RequestParam(value = "rating", required = false) Integer rating,
            HttpSession session
    ) {
       
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        Recipe recipe = recipeService.findById(recipeId).orElse(null);
        if (recipe == null) {
            return "redirect:/recipe_list";
        }

        Comment comment = new Comment();
        comment.setRecipe(recipe);
        comment.setAuthor(currentUser);
        comment.setContent(content);
        comment.setRating(rating);
        comment.setCreatedAt(LocalDateTime.now());
        
        commentService.save(comment);

        
        return "redirect:/recipe/" + recipeId;
    }
}
