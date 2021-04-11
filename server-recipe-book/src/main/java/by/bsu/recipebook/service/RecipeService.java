package by.bsu.recipebook.service;

import by.bsu.recipebook.constants.Constants;
import by.bsu.recipebook.dto.CommentDto;
import by.bsu.recipebook.dto.IngredientDto;
import by.bsu.recipebook.dto.recipe.*;
import by.bsu.recipebook.entity.*;
import by.bsu.recipebook.exception.ServiceException;
import by.bsu.recipebook.mapper.CommentMapper;
import by.bsu.recipebook.mapper.JsonMapper;
import by.bsu.recipebook.mapper.RecipeMapper;
import by.bsu.recipebook.repository.*;
import by.bsu.recipebook.validator.SortType;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RecipeService {
    private static final Logger logger = LogManager.getLogger(RecipeService.class);

    private final RecipeRepository recipeRepository;

    private final AuthService authService;

    private final RecipeMapper recipeMapper;

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final IngredientRepository ingredientRepository;

    private final RecipeLikeRepository recipeLikeRepository;

    private final JsonMapper jsonMapper;

    private final CategoryRepository categoryRepository;

    @Transactional
    public RecipeGetDto save(String jsonRecipeDto, MultipartFile file) {
        String filePath = ImageService.save(file, Constants.RECIPE_UPLOAD_IMAGES_DIR);
        User user = authService.getCurrentUser();
        Recipe savedRecipe = recipeRepository
                .save(recipeMapper.mapToRecipe(
                        jsonMapper.mapDto(jsonRecipeDto, RecipePostDto.class), user, filePath));
        return recipeMapper.mapToRecipeGetDto(savedRecipe);
    }

    @Transactional
    public void delete(int id) {
        recipeRepository.deleteById(id);
    }

    @Transactional
    public RecipeGetDto update(RecipeUpdateDto recipeUpdateDto, int id) throws ServiceException {
        Recipe recipe = getRecipeById(id);
        Recipe updatedRecipe = recipeMapper.mapFromRecipeUpdateDto(recipeUpdateDto, recipe);
        return recipeMapper.mapToRecipeGetDto(recipeRepository.save(updatedRecipe));
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getAll(int page, int size, SortType sortType) {
        Page<Recipe> pageTuts;
        switch (sortType) {
            case BY_DATE_ASC:
                pageTuts = getSortedRecipes(page, size, Sort.Direction.ASC, "createdDate");
                break;
            case BY_DATE_DESC:
                pageTuts = getSortedRecipes(page, size, Sort.Direction.DESC, "createdDate");
                break;
            case BY_RECIPE_NAME:
                pageTuts = getSortedRecipes(page, size, Sort.Direction.ASC, "recipeName");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + sortType);
        }
        return getSortedData(pageTuts);
    }

    private Page<Recipe> getSortedRecipes(int page, int size,
        Sort.Direction direction, String property) {
        return recipeRepository
                        .findAll(PageRequest.of(page, size,
                                Sort.by(direction, property)));

    }

    @Transactional(readOnly = true)
    public RecipeGetDto getByRecipeName(String recipeName) {
        return recipeMapper.mapToRecipeGetDto(recipeRepository
                        .findByRecipeName(recipeName));
    }

    @Transactional(readOnly = true)
    public RecipeGetDto getById(int id) throws ServiceException {
        return recipeMapper.mapToRecipeGetDto(getRecipeById(id));
    }

    @Transactional(readOnly = true)
    public String getRecipeMainPicture(int id) throws ServiceException {
        String recipeMainPicture = getRecipeById(id).getMainPicture();
        try {
            return DatatypeConverter
                    .printBase64Binary(Files
                            .readAllBytes(Paths.get(recipeMainPicture)));
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error while get recipe main picture: ", e);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public RecipeGetDto getRandomRecipe() throws ServiceException {
        long count = recipeRepository.count();
        int randomNumber = (int)(Math.random() * count);
        Page<Recipe> recipePage = recipeRepository
                .findAll(PageRequest.of(randomNumber, 1));
        Recipe recipe = recipePage.stream()
                .findFirst()
                .orElseThrow(() -> new ServiceException("Error occurs while get random recipe"));
        return recipeMapper.mapToRecipeGetDto(recipe);
    }

    private Map<String, Object> getSortedData(Page<Recipe> pageTuts) {
        List<RecipeGetDto> recipeGetDtoList = pageTuts.getContent().stream()
                .map(recipeMapper::mapToRecipeGetDto)
                .collect(Collectors.toList());
        return getRecipeResponseAsMap(recipeGetDtoList, pageTuts);
    }

    private Map<String, Object> getRecipeResponseAsMap(Object object, Page<Recipe> pageTuts) {
        Map<String, Object> response = new HashMap<>();
        response.put("recipes", object);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalItems", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        return response;
    }

    @Transactional(readOnly = true)
    public List<RecipeGetDto> getByRecipeNamePattern(String pattern) {
        return recipeRepository.searchByRecipeNamePattern(pattern)
                .stream()
                .map(recipeMapper::mapToRecipeGetDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto saveComment(int id, CommentDto commentDto) throws ServiceException {
        System.out.println(commentDto.toString());
        User user = authService.getCurrentUser();
        Recipe recipe = getRecipeById(id);
        Comment comment = commentMapper
                .mapToComment(commentDto, user, recipe);
        return commentMapper
                .mapToCommentDto(commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getAllRecipeComments(int id) throws ServiceException {
        Recipe recipe = getRecipeById(id);
        List<Comment> commentList = recipe.getComments()
                .stream()
                .filter(comment -> comment.getParent() == null)
                .collect(Collectors.toList());
        return commentList.stream()
                .map(commentMapper::mapToCommentDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void like(RecipeLikeDto recipeLikeDto) throws ServiceException {
        if (authService.isLoggedIn()) {
            Recipe recipe = getRecipeById(recipeLikeDto.getIdRecipe());
            User currentUser = authService.getCurrentUser();
            LikeType likeType = recipeLikeDto.getLikeType();
            RecipeLike recipeLike;
            Optional<RecipeLike> recipeLikeOptional =
                    getRecipeLikeByRecipeAndUser(recipe, currentUser);
            if (recipeLikeOptional.isEmpty()) {
                recipeLike = recipeMapper.mapToRecipeLike(recipeLikeDto, currentUser, recipe);
            } else {
                recipeLike = recipeLikeOptional.get();
                recipeLike.setLikeType(likeType);
            }
            recipeLikeRepository.save(recipeLike);
            likeRecipe(recipe, likeType);
            recipeRepository.save(recipe);
        }
    }

    private void likeRecipe(Recipe recipe, LikeType likeType) {
        if (LikeType.LIKE.equals(likeType)) {
            recipe.setLikeCount(recipe.getLikeCount() + 1);
        } else {
            recipe.setLikeCount(recipe.getLikeCount() - 1);
        }
    }

    private Recipe getRecipeById(int id) throws ServiceException {
        return recipeRepository
                .findById(id)
                .orElseThrow(
                        () -> new ServiceException(
                                "Error occurs while trying to get recipe with such " + id));
    }

    private Optional<RecipeLike> getRecipeLikeByRecipeAndUser(Recipe recipe, User user) {
        return recipeLikeRepository.findByRecipeAndUser(recipe, user);
    }

    @Transactional
    public boolean isLiked(int idRecipe) throws ServiceException {
        boolean isLiked = false;
        Recipe recipe = getRecipeById(idRecipe);
        Optional<RecipeLike> recipeLikeOptional =
                getRecipeLikeByRecipeAndUser(recipe, authService.getCurrentUser());
        if (recipeLikeOptional.isPresent()) {
            isLiked = recipeLikeOptional.get().getLikeType().equals(LikeType.LIKE);
        }
        return isLiked;
    }

    @Transactional
    public CommentDto replyOnComment(int id, CommentDto commentDto) throws ServiceException {
        User user = authService.getCurrentUser();
        Recipe recipe = getRecipeById(id);
        Comment comment = commentMapper.mapToComment(commentDto, user, recipe);
        Optional<Comment> parentOptional = commentRepository
                .findById(commentDto.getIdParent());
        Comment parent = null;
        if (parentOptional.isPresent()) {
            parent = parentOptional.get();
        }
        comment.setParent(parent);
        return commentMapper.mapToCommentDto(commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getRecipesByIngredients(List<IngredientDto> ingredientDtoList,
                                                          int page, int size) {
        List<Integer> ingredients = new ArrayList<>();
                ingredientDtoList.forEach(ingredientDto -> {
                    Ingredient ingredient = ingredientRepository
                            .findByIngredientName(
                                    ingredientDto.getIngredientName());
                    ingredients.add(ingredient.getIdIngredient());
                });
        Page<Recipe> pageTuts = recipeRepository
                .findRecipesByIngredients(ingredients, PageRequest.of(page, size));
        List<RecipeDetailsDto> recipeDetailsDtoList = getRecipeDetailsDto(pageTuts);
        return getRecipeResponseAsMap(recipeDetailsDtoList, pageTuts);
    }

    private List<RecipeDetailsDto> getRecipeDetailsDto(Page<Recipe> pageTuts) {
        return pageTuts
                .getContent()
                .stream()
                .map(recipeMapper::mapToRecipeDetailsDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getRecipesByCategory(String categoryName, int page, int size) {
        Category category = categoryRepository.findByCategoryName(categoryName);
        Page<Recipe> pageTuts = recipeRepository
                .findRecipesByCategory(category, PageRequest.of(page, size));
        List<RecipeDetailsDto> recipeDetailsDtoList = getRecipeDetailsDto(pageTuts);
        return getRecipeResponseAsMap(recipeDetailsDtoList, pageTuts);
    }
}
