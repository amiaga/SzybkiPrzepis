package ksi.szybkiprzepis.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import ksi.szybkiprzepis.models.Category;
import ksi.szybkiprzepis.models.Recipe;
import ksi.szybkiprzepis.models.Tag;
import ksi.szybkiprzepis.models.User;
import ksi.szybkiprzepis.services.CategoryService;
import ksi.szybkiprzepis.services.RecipeService;
import ksi.szybkiprzepis.services.TagService;

@Controller
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @GetMapping("/recipe_list")
    public String viewRecipeList(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "category", required = false) Long categoryId,
            @RequestParam(value = "difficulty", required = false) String difficulty,
            @RequestParam(value = "tag", required = false) Long tagId,
            Model model
    ) {
        List<Recipe> recipeList = recipeService.findAll();

        
        if (categoryId != null) {
            recipeList = recipeList.stream()
                    .filter(r -> r.getCategory() != null && r.getCategory().getId().equals(categoryId))
                    .collect(Collectors.toList());
        }

        
        if (search != null && !search.trim().isEmpty()) {
            String searchLower = search.trim().toLowerCase();
            recipeList = recipeList.stream()
                    .filter(r -> r.getTitle() != null && r.getTitle().toLowerCase().contains(searchLower))
                    .collect(Collectors.toList());
        }

      
        if (difficulty != null && !difficulty.isEmpty()) {
            recipeList = recipeList.stream()
                    .filter(r -> difficulty.equals(r.getDifficultyLevel()))
                    .collect(Collectors.toList());
        }

       
        if (tagId != null) {
            recipeList = recipeList.stream()
                    .filter(r -> r.getTags() != null &&
                            r.getTags().stream().anyMatch(t -> t.getId().equals(tagId)))
                    .collect(Collectors.toList());
        }

        model.addAttribute("recipeList", recipeList);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("allTags", tagService.findAll());
        return "recipe_list";
    }

    @GetMapping("/recipe/{id}")
    public String viewRecipe(@PathVariable("id") Long id, Model model) {
        Recipe recipe = recipeService.findById(id).orElse(null);
        if (recipe == null) {
            return "redirect:/recipe_list";
        }
        model.addAttribute("recipe", recipe);
        return "recipe_details";
    }

    @GetMapping("/new_recipe")
    public String showNewRecipeForm(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        Recipe recipe = new Recipe();
        model.addAttribute("recipe", recipe);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("tags", tagService.findAll());
        return "new_recipe";
    }

   
    @PostMapping("/save_recipe")
    public String saveRecipe(
            @ModelAttribute Recipe recipe,                  // bindowane pola: title, ingredients, ...
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "tagsIds", required = false) List<Long> tagsIds,
            HttpSession session
    ) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        
        recipe.setAuthor((User) session.getAttribute("user"));
        recipe.setCreationDate(LocalDateTime.now());

        
        if (categoryId != null) {
            Category cat = categoryService.findById(categoryId).orElse(null);
            recipe.setCategory(cat);
        }

        
        if (tagsIds != null) {
            List<Tag> selectedTags = tagsIds.stream()
                    .map(tagService::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            recipe.setTags(selectedTags);
        } else {
            recipe.setTags(null);
        }

        recipeService.save(recipe);
        return "redirect:/recipe_list";
    }

    /
    @GetMapping("/edit_recipe/{id}")
    public String showEditRecipeForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        Recipe recipe = recipeService.findById(id).orElse(null);
        User currentUser = (User) session.getAttribute("user");

        if (recipe == null || currentUser == null) {
            return "redirect:/recipe_list";
        }
        if (!currentUser.getRole().equals("ADMIN") &&
            !recipe.getAuthor().getId().equals(currentUser.getId())) {
            return "redirect:/recipe_list";
        }

        model.addAttribute("recipe", recipe);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("tags", tagService.findAll());
        return "edit_recipe";
    }

    @PostMapping("/update_recipe/{id}")
    public String updateRecipe(
            @PathVariable("id") Long id,
            @ModelAttribute Recipe recipe,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "tagsIds", required = false) List<Long> tagsIds,
            HttpSession session
    ) {
        Recipe existingRecipe = recipeService.findById(id).orElse(null);
        User currentUser = (User) session.getAttribute("user");

        if (existingRecipe == null || currentUser == null) {
            return "redirect:/recipe_list";
        }
        if (!currentUser.getRole().equals("ADMIN") &&
            !existingRecipe.getAuthor().getId().equals(currentUser.getId())) {
            return "redirect:/recipe_list";
        }

        
        recipe.setId(id);
        recipe.setAuthor(existingRecipe.getAuthor());
        recipe.setCreationDate(existingRecipe.getCreationDate());

        
        Category cat = categoryService.findById(categoryId).orElse(null);
        recipe.setCategory(cat);

        
        if (tagsIds != null) {
            List<Tag> selectedTags = tagsIds.stream()
                    .map(tagService::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            recipe.setTags(selectedTags);
        } else {
            recipe.setTags(null);
        }

        recipeService.save(recipe);
        return "redirect:/recipe/" + id;
    }

    
    @GetMapping("/delete_recipe/{id}")
    public String deleteRecipe(@PathVariable("id") Long id, HttpSession session) {
        Recipe recipe = recipeService.findById(id).orElse(null);
        User currentUser = (User) session.getAttribute("user");

        if (recipe == null || currentUser == null) {
            return "redirect:/recipe_list";
        }
        if (!currentUser.getRole().equals("ADMIN") &&
            !recipe.getAuthor().getId().equals(currentUser.getId())) {
            return "redirect:/recipe_list";
        }
        recipeService.deleteById(id);

        return "redirect:/recipe_list";
    }
}
