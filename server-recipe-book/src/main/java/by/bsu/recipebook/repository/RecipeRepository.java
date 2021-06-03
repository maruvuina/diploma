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

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    @Query("select r from Recipe r where r.recipeName=:recipeName and r.isDelete=false")
    Recipe findByRecipeName(String recipeName);

    @Nonnull
    @Override
    @Query("select r from Recipe r where r.idRecipe=:id and r.isDelete=false")
    Optional<Recipe> findById(@Param("id") @Nonnull Integer id);

    @Override
    @Query("update Recipe r set r.isDelete=true where r.idRecipe=:id")
    @Modifying
    void deleteById(@Param("id") @Nonnull Integer id);

    @Nonnull
    @Override
    @Query("select r from Recipe r where r.isDelete=false")
    Page<Recipe> findAll(@Nonnull Pageable pageable);

    @Override
    @Query("select count(r) from Recipe r where r.isDelete=false")
    long count();

    @Query("select r from Recipe as r where r.recipeName like %:pattern% and r.isDelete=false order by r.recipeName")
    List<Recipe> searchByRecipeNamePattern(@Param("pattern") String pattern);

    @Query("select r from Recipe as r " +
            "inner join r.ingredientAmountSet as ingSet " +
            "inner join ingSet.ingredient as ing " +
            "where ing.idIngredient in (:ingredients) and r.isDelete=false " +
            "group by r.recipeName")
    Page<Recipe> findRecipesByIngredients(@Param("ingredients") Collection<Integer> ingredients,
                                          Pageable pageable);

    @Query("select r from Recipe as r where r.category = :category and r.isDelete=false order by r.recipeName")
    Page<Recipe> findRecipesByCategory(@Param("category") Category category, Pageable pageable);

    @Query("select r from Recipe as r where r.author = :author and r.isDelete=false order by r.recipeName")
    Page<Recipe> findRecipesByAuthor(@Param("author") User author, Pageable pageable);

    @Query("select r from Recipe as r " +
            "inner join r.tagSet as tSet " +
            "where tSet.idTag in (:tags) and r.isDelete=false " +
            "group by r.recipeName")
    Page<Recipe> findRecipesByTag(@Param("tags") Collection<Integer> tags, Pageable pageable);

    @Query("select r from Recipe as r " +
            "inner join r.cuisineSet as cSet " +
            "where cSet.idCuisine in (:cuisines) and r.isDelete=false " +
            "group by r.recipeName")
    Page<Recipe> findRecipesByCuisine(@Param("cuisines") Collection<Integer> cuisines, Pageable pageable);

    boolean existsByRecipeName(String recipeName);
}
