package by.bsu.recipebook.repository;

import by.bsu.recipebook.entity.Recipe;
import by.bsu.recipebook.entity.RecipeLike;
import by.bsu.recipebook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeLikeRepository extends JpaRepository<RecipeLike, Integer> {
    Optional<RecipeLike> findByRecipeAndUser(Recipe recipe, User currentUser);
}
