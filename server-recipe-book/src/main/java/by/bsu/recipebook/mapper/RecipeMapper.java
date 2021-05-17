package by.bsu.recipebook.mapper;

import by.bsu.recipebook.dto.*;
import by.bsu.recipebook.dto.recipe.*;
import by.bsu.recipebook.entity.*;
import by.bsu.recipebook.repository.*;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CuisineRepository cuisineRepository;

    @Autowired
    private CuisineMapper cuisineMapper;

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
    @Mapping(target = "tagSet", ignore = true)
    @Mapping(target = "cuisineSet", ignore = true)
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
                    Ingredient ingredient = ingredientRepository.findByIngredientName(o.getIngredientName());
                    IngredientAmount ingredientAmount = new IngredientAmount();
                    ingredientAmount.setIngredient(ingredient);
                    ingredientAmount.setMeasureAmount(o.getMeasureAmount());
                    recipe.addIngredientAmount(ingredientAmountRepository.save(ingredientAmount));
                });
    }

    @AfterMapping
    void setRecipeTags(RecipePostDto recipePostDto, @MappingTarget Recipe recipe) {
        recipePostDto.getTags().forEach(o -> recipe.addTag(tagRepository.findByTagName(o.getTagName())));
    }

    @AfterMapping
    void setRecipeCuisines(RecipePostDto recipePostDto, @MappingTarget Recipe recipe) {
        recipePostDto.getCuisines()
                .forEach(o ->
                        recipe.addCuisine(cuisineRepository.findByCuisineName(o.getCuisineName())));
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
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "likesCount", source = "recipe.likeCount")
    @Mapping(target = "idAuthor", expression = "java(recipe.getAuthor().getIdUser())")
    @Mapping(target = "cuisines", ignore = true)
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

    @AfterMapping
    void setRecipeGetDtoTags(Recipe recipe, @MappingTarget RecipeGetDto recipeGetDto) {
        List<TagDto> tagDtoList = recipe.getTagSet()
                .stream()
                .map(tagMapper::mapToTagDto)
                .collect(Collectors.toList());
        recipeGetDto.setTags(tagDtoList);
    }

    @AfterMapping
    void setRecipeGetDtoCuisines(Recipe recipe, @MappingTarget RecipeGetDto recipeGetDto) {
        List<CuisineDto> cuisineDtoList = recipe.getCuisineSet()
                .stream()
                .map(cuisineMapper::mapToCuisineDto)
                .collect(Collectors.toList());
        recipeGetDto.setCuisines(cuisineDtoList);
    }

    @Mapping(target = "idRecipe", source = "recipe.idRecipe")
    @Mapping(target = "recipeName", source = "recipe.recipeName")
    @Mapping(target = "announce", source = "recipe.announce")
    @Mapping(target = "authorName", expression = "java(recipe.getAuthor().getFullName())")
    @Mapping(target = "likesCount", source = "recipe.likeCount")
    @Mapping(target = "idAuthor", expression = "java(recipe.getAuthor().getIdUser())")
    public abstract RecipeDetailsDto mapToRecipeDetailsDto(Recipe recipe);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateRecipe(RecipeUpdateDto recipeUpdateDto, @MappingTarget Recipe recipe);

    @Mapping(target = "likeType", source = "recipeLikeDto.likeType")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "recipe", source = "recipe")
    public abstract RecipeLike mapToRecipeLike(RecipeLikeDto recipeLikeDto, User user, Recipe recipe);
}
