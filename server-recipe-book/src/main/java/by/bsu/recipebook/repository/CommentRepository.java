package by.bsu.recipebook.repository;

import by.bsu.recipebook.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer>  {

}
