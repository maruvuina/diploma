package by.bsu.recipebook.mapper;

import by.bsu.recipebook.dto.CommentDto;
import by.bsu.recipebook.dto.IngredientDto;
import by.bsu.recipebook.dto.InstructionDto;
import by.bsu.recipebook.dto.recipe.*;
import by.bsu.recipebook.entity.*;
import by.bsu.recipebook.repository.CategoryRepository;
import by.bsu.recipebook.repository.IngredientAmountRepository;
import by.bsu.recipebook.repository.IngredientRepository;
import by.bsu.recipebook.util.JsonNullableUtils;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class RecipeMapper {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private InstructionMapper instructionMapper;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private IngredientAmountRepository ingredientAmountRepository;

    @Autowired
    private IngredientMapper ingredientMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Mapping(target = "recipeName", source = "recipePostDto.recipeName")
    @Mapping(target = "cookingTime", source = "recipePostDto.cookingTime")
    @Mapping(target = "yield", source = "recipePostDto.yield")
    @Mapping(target = "mainPicture", source = "recipeMainPictureLocation")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "author", source = "user")
    @Mapping(target = "category", expression = "java(getCategory(recipePostDto))")
    @Mapping(target = "announce", source = "recipePostDto.announce")
    @Mapping(target = "instructions", ignore = true)
    @Mapping(target = "ingredientAmountSet", ignore = true)
    public abstract Recipe mapToRecipe(RecipePostDto recipePostDto, User user, String recipeMainPictureLocation);

    Category getCategory(RecipePostDto recipePostDto) {
        return categoryRepository.findByCategoryName(recipePostDto.getCategoryName());
    }

    @AfterMapping
    void setRecipeInstructions(RecipePostDto recipePostDto, @MappingTarget Recipe recipe) {
        recipePostDto.getInstructions()
                .stream()
                .map(instructionMapper::mapToInstruction)
                .forEach(recipe::addInstruction);
    }

    @AfterMapping
    void setRecipeIngredients(RecipePostDto recipePostDto, @MappingTarget Recipe recipe) {
        recipePostDto.getIngredients()
                .forEach(o -> {
                    Ingredient ingredient =
                            ingredientRepository
                                    .findByIngredientName(o.getIngredientName());
                    IngredientAmount ingredientAmount = new IngredientAmount();
                    ingredientAmount.setIngredient(ingredient);
                    ingredientAmount.setMeasureAmount(o.getMeasureAmount());
                    recipe.addIngredientAmount(ingredientAmountRepository.save(ingredientAmount));
                });
    }

    @Mapping(target = "idRecipe", source = "recipe.idRecipe")
    @Mapping(target = "recipeName", source = "recipe.recipeName")
    @Mapping(target = "cookingTime", source = "recipe.cookingTime")
    @Mapping(target = "yield", source = "recipe.yield")
    @Mapping(target = "createdDate", expression = "java(getCreatedDate(recipe))")
    @Mapping(target = "authorName", expression = "java(recipe.getAuthor().getFullName())")
    @Mapping(target = "announce", source = "recipe.announce")
    @Mapping(target = "categoryName", expression = "java(recipe.getCategory().getCategoryName())")
    @Mapping(target = "ingredients", ignore = true)
    @Mapping(target = "instructions", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "likesCount", source = "recipe.likeCount")
    public abstract RecipeGetDto mapToRecipeGetDto(Recipe recipe);

    String getCreatedDate(Recipe recipe) {
        return TimeAgo.using(recipe.getCreatedDate().toEpochMilli());
    }

    @AfterMapping
    void setRecipeGetDtoInstructions(Recipe recipe, @MappingTarget RecipeGetDto recipeGetDto) {
        List<InstructionDto> instructionDtoList = recipe.getInstructions()
                .stream()
                .map(instructionMapper::mapToInstructionDto)
                .collect(Collectors.toList());
        recipeGetDto.setInstructions(instructionDtoList);
    }

    @AfterMapping
    void setRecipeGetDtoIngredients(Recipe recipe, @MappingTarget RecipeGetDto recipeGetDto) {
        List<IngredientDto> ingredientDtoList = recipe.getIngredientAmountSet()
                .stream()
                .map(ingredientMapper::mapToIngredientDto)
                .collect(Collectors.toList());
        recipeGetDto.setIngredients(ingredientDtoList);
    }

    @AfterMapping
    void setRecipeGetDtoComments(Recipe recipe, @MappingTarget RecipeGetDto recipeGetDto) {
        List<CommentDto> commentDtoList = recipe.getComments()
                .stream()
                .map(commentMapper::mapToCommentDto)
                .collect(Collectors.toList());
        recipeGetDto.setComments(commentDtoList);
    }

    @Mapping(target = "idRecipe", source = "recipe.idRecipe")
    @Mapping(target = "recipeName", source = "recipe.recipeName")
    @Mapping(target = "announce", source = "recipe.announce")
    @Mapping(target = "authorName", expression = "java(recipe.getAuthor().getFullName())")
    @Mapping(target = "likesCount", source = "recipe.likeCount")
    public abstract RecipeDetailsDto mapToRecipeDetailsDto(Recipe recipe);

    public Recipe mapFromRecipeUpdateDto(RecipeUpdateDto recipeUpdateDto, Recipe recipe) {
        JsonNullableUtils.changeIfPresent(recipeUpdateDto.getRecipeName(),
                recipe::setRecipeName);
        JsonNullableUtils.changeIfPresent(recipeUpdateDto.getCookingTime(),
                recipe::setCookingTime);
        JsonNullableUtils.changeIfPresent(recipeUpdateDto.getYield(),
                recipe::setYield);
        JsonNullableUtils.changeIfPresent(recipeUpdateDto.getAnnounce(),
                recipe::setAnnounce);
        @NotNull JsonNullable<String> categoryName = recipeUpdateDto.getCategoryName();
        if (categoryName.isPresent()) {
            Category category = categoryRepository
                    .findByCategoryName(categoryName.get());
            recipe.setCategory(category);
        }
        return recipe;
    }

    @Mapping(target = "likeType", source = "recipeLikeDto.likeType")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "recipe", source = "recipe")
    public abstract RecipeLike mapToRecipeLike(RecipeLikeDto recipeLikeDto, User user, Recipe recipe);
}
