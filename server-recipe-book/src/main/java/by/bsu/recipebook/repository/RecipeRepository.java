package by.bsu.recipebook.repository;

import by.bsu.recipebook.entity.Category;
import by.bsu.recipebook.entity.Recipe;
import by.bsu.recipebook.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNullApi;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    Recipe findByRecipeName(String recipeName);

    @Override
    @Query("update Recipe r set r.isDelete=true where r.idRecipe=:id")
    @Modifying
    void deleteById(@Param("id") Integer id);

    @Override
    @NotNull
    @Query("select r from Recipe r where r.isDelete=false")
    List<Recipe> findAll();

    @Override
    @Query("select count(r) from Recipe r")
    long count();

    @Query("select r from Recipe as r where r.recipeName like %:pattern% order by r.recipeName")
    List<Recipe> searchByRecipeNamePattern(@Param("pattern") String pattern);

    @Query("select r from Recipe as r " +
            "inner join r.ingredientAmountSet as ingSet " +
            "inner join ingSet.ingredient as ing " +
            "where ing.idIngredient in (:ingredients) " +
            "order by r.recipeName")
    Page<Recipe> findRecipesByIngredients(@Param("ingredients") Collection<Integer> ingredients,
                                          Pageable pageable);

    @Query("select r from Recipe as r where r.category = :category order by r.recipeName")
    Page<Recipe> findRecipesByCategory(@Param("category") Category category, Pageable pageable);

    @Query("select r from Recipe as r where r.author = :author order by r.recipeName")
    Page<Recipe> findRecipesByAuthor(@Param("author") User author, Pageable pageable);
}
