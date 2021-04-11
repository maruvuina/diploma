package by.bsu.recipebook.repository;

import by.bsu.recipebook.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    Ingredient findByIngredientName(String ingredientName);

    @Query("select i from Ingredient as i where i.ingredientName like %:pattern% order by i.ingredientName")
    List<Ingredient> searchByIngredientNamePattern(@Param("pattern") String pattern);
}
