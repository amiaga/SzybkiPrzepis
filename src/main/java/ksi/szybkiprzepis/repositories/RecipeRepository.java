// Path: src/main/java/ksi/szybkiprzepis/repositories/RecipeRepository.java

package ksi.szybkiprzepis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ksi.szybkiprzepis.models.Recipe;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByTitleContainingIgnoreCase(String title);
    List<Recipe> findByDifficultyLevel(String difficultyLevel);
    List<Recipe> findByCategoryId(Long categoryId);
}