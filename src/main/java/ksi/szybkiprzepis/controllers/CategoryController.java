package ksi.szybkiprzepis.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ksi.szybkiprzepis.models.Category;
import ksi.szybkiprzepis.services.CategoryService;
import jakarta.servlet.http.HttpSession;
import ksi.szybkiprzepis.models.User;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService service;

    @RequestMapping("/category_list")
    public String viewCategoryList(Model model, HttpSession session) {
        List<Category> categoryList = service.findAll();
        model.addAttribute("categoryList", categoryList);
        return "category_list";
    }

    @RequestMapping("/new_category")
    public String showNewCategoryForm(Model model, HttpSession session) {
        
         User currentUser = (User) session.getAttribute("user");
         if (currentUser == null || !currentUser.getRole().equals("ADMIN")) {
            return "redirect:/login";
         }

        Category category = new Category();
        model.addAttribute("category", category);
        return "new_category";
    }

    @PostMapping("/save_category")
    public String saveCategory(@ModelAttribute("category") Category category, HttpSession session) {
        // (opcjonalnie) sprawdź, czy user jest adminem

        service.save(category);
        return "redirect:/category_list";
    }

   
    @GetMapping("/edit_category/{id}")
    public String showEditCategoryForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        
        // if (!isAdmin(session)) { return "redirect:/login"; }

        Category category = service.findById(id).orElse(null);
        if (category == null) {
            return "redirect:/category_list?error=not-found";
        }
        model.addAttribute("category", category);
        return "edit_category";
    }

    // Zapis edytowanej kategorii
    @PostMapping("/update_category/{id}")
    public String updateCategory(@PathVariable("id") Long id, 
                                 @ModelAttribute("category") Category category,
                                 HttpSession session) {
        Category existing = service.findById(id).orElse(null);
        if (existing == null) {
            return "redirect:/category_list?error=not-found";
        }
     
        category.setId(id);
        service.save(category);

        return "redirect:/category_list?success=updated";
    }

  
    @GetMapping("/delete_category/{id}")
    public String deleteCategory(@PathVariable("id") Long id, HttpSession session) {
        service.deleteById(id);
        return "redirect:/category_list?success=deleted";
    }
}
