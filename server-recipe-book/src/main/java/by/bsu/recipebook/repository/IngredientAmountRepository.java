package by.bsu.recipebook.repository;

import by.bsu.recipebook.entity.IngredientAmount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientAmountRepository extends JpaRepository<IngredientAmount, Integer> {
}
