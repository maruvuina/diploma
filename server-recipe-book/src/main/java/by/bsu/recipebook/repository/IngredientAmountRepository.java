package by.bsu.recipebook.repository;

import by.bsu.recipebook.entity.IngredientAmount;
import by.bsu.recipebook.entity.embeddable.RecipeIngredientKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientAmountRepository extends JpaRepository<IngredientAmount, Integer> {
}
