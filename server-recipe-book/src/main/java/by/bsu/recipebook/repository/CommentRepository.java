package by.bsu.recipebook.repository;

import by.bsu.recipebook.entity.Comment;
import by.bsu.recipebook.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer>  {

    List<Comment> findByRecipe(Recipe recipe);
}
