package ksi.szybkiprzepis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import ksi.szybkiprzepis.models.Tag;
import ksi.szybkiprzepis.models.User;
import ksi.szybkiprzepis.services.TagService;

import java.util.List;

@Controller
@RequestMapping("/admin/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && "ADMIN".equals(user.getRole());
    }

    @GetMapping("")
    public String viewTagList(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        List<Tag> tagList = tagService.findAll();
        model.addAttribute("tagList", tagList);
        return "admin/tags";  // <-- widok lista tagów
    }

    @GetMapping("/new")
    public String showNewTagForm(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        Tag tag = new Tag();
        model.addAttribute("tag", tag);
        return "admin/new_tag";  
    }

    @PostMapping("/save")
    public String saveTag(@ModelAttribute("tag") Tag tag, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        tagService.save(tag);
        return "redirect:/admin/tags?success=created";
    }

    @GetMapping("/delete/{id}")
    public String deleteTag(@PathVariable("id") Long id, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        tagService.deleteById(id);
        return "redirect:/admin/tags?success=deleted";
    }
}
