// Path: src/main/java/ksi/szybkiprzepis/services/RecipeService.java

package ksi.szybkiprzepis.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ksi.szybkiprzepis.models.Recipe;
import ksi.szybkiprzepis.repositories.RecipeRepository;

@Service
public class RecipeService {
    
    @Autowired
    private RecipeRepository repository;

    public List<Recipe> findAll() {
        return repository.findAll();
    }

    public Optional<Recipe> findById(Long id) {
        return repository.findById(id);
    }

    public Recipe save(Recipe recipe) {
        return repository.save(recipe);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Recipe> findByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }

    public List<Recipe> findByDifficulty(String difficulty) {
        return repository.findByDifficultyLevel(difficulty);
    }

    public List<Recipe> findByCategory(Long categoryId) {
        return repository.findByCategoryId(categoryId);
    }
}