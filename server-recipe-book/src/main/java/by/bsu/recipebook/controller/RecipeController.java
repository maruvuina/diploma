package by.bsu.recipebook.controller;

import by.bsu.recipebook.dto.CommentDto;
import by.bsu.recipebook.dto.IngredientDto;
import by.bsu.recipebook.dto.recipe.RecipeGetDto;
import by.bsu.recipebook.dto.recipe.RecipeLikeDto;
import by.bsu.recipebook.dto.recipe.RecipeUpdateDto;
import by.bsu.recipebook.exception.ServiceException;
import by.bsu.recipebook.service.RecipeService;
import by.bsu.recipebook.validator.SortType;
import by.bsu.recipebook.validator.transfer.Details;
import by.bsu.recipebook.validator.transfer.Request;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/recipes")
@RestController
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping(consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RecipeGetDto> save(@RequestPart("file") @Valid @NotNull @NotBlank MultipartFile file,
        @RequestParam("recipeDto") @Valid @NotNull String jsonRecipeDto) {
        return new ResponseEntity<>(recipeService.save(jsonRecipeDto, file),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        recipeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size, @RequestParam(name = "sort") @NotBlank String sortType) {
        return new ResponseEntity<>(recipeService.getAll(page, size, SortType.valueOf(sortType)),
                HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<RecipeGetDto> getByRecipeName(
            @RequestParam(value = "name") @NotBlank String name) {
        return new ResponseEntity<>(recipeService.getByRecipeName(name), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeGetDto> getById(@PathVariable("id") int id) throws ServiceException {
        return new ResponseEntity<>(recipeService.getById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RecipeGetDto> update(@RequestBody @Valid RecipeUpdateDto recipeUpdateDto,
                                               @PathVariable("id") int id) throws ServiceException {
        return new ResponseEntity<>(recipeService.update(recipeUpdateDto, id), HttpStatus.OK);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<String> getRecipeMainPicture(@PathVariable("id") int id) throws ServiceException {
        return new ResponseEntity<>(recipeService.getRecipeMainPicture(id), HttpStatus.OK);
    }

    @GetMapping("/random")
    public ResponseEntity<RecipeGetDto> getRandomRecipe() {
        return new ResponseEntity<>(recipeService.getRandomRecipe(), HttpStatus.OK);
    }

    @GetMapping("/search/{pattern}")
    public ResponseEntity<List<RecipeGetDto>> getRecipeByRecipeNamePattern(
            @PathVariable("pattern") @NotBlank String pattern) {
        return new ResponseEntity<>(recipeService.getByRecipeNamePattern(pattern), HttpStatus.OK);
    }

    @PostMapping("/{id}/comments")
    @PreAuthorize("hasRole('USER')")
    @JsonView({Details.class})
    public ResponseEntity<CommentDto> createComment(@PathVariable("id") int id,
        @RequestBody @Validated(Request.class) CommentDto commentDto) throws ServiceException {
        return new ResponseEntity<>(recipeService.saveComment(id, commentDto), HttpStatus.CREATED);
    }

    @JsonView({Details.class})
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> getAllRecipeComments(@PathVariable("id") int id) throws ServiceException {
        return new ResponseEntity<>(recipeService.getAllRecipeComments(id), HttpStatus.OK);
    }

    @PostMapping("/likes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> like(@RequestBody RecipeLikeDto recipeLikeDto) throws ServiceException {
        recipeService.like(recipeLikeDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/likes/{idRecipe}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Boolean> isLiked(@PathVariable("idRecipe") int idRecipe) throws ServiceException {
        return new ResponseEntity<>(recipeService.isLiked(idRecipe), HttpStatus.OK);
    }

    @PostMapping("/{id}/comments/reply")
    @JsonView({Details.class})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommentDto> createReplyComment(@PathVariable("id") int id,
        @RequestBody @Validated(Request.class) CommentDto commentDto) throws ServiceException {
        return new ResponseEntity<>(recipeService.replyOnComment(id, commentDto), HttpStatus.CREATED);
    }

    @PostMapping(path = "/ingredients")
    @JsonView({Details.class})
    public ResponseEntity<Map<String, Object>> getRecipesByIngredients(
            @RequestBody @Valid List<IngredientDto> ingredientDtoList,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return new ResponseEntity<>(recipeService
                .getRecipesByIngredients(ingredientDtoList, page, size), HttpStatus.OK);
    }

    @GetMapping("/categories")
    @JsonView({Details.class})
    public ResponseEntity<Map<String, Object>> getRecipesByCategory(
            @RequestParam(value = "categoryName") String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return new ResponseEntity<>(recipeService
                .getRecipesByCategory(categoryName, page, size), HttpStatus.OK);
    }

    @GetMapping("/authors/{idAuthor}")
    public ResponseEntity<Map<String, Object>> getRecipesByAuthor(
            @PathVariable("idAuthor") int idAuthor,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) throws ServiceException {
        return new ResponseEntity<>(recipeService
                .getRecipesByAuthor(idAuthor, page, size), HttpStatus.OK);
    }

    @GetMapping("/tags")
    public ResponseEntity<Map<String, Object>> getRecipesByTag(
            @RequestParam(value = "tagName") String tagName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return new ResponseEntity<>(recipeService
                .getRecipesByTag(tagName, page, size), HttpStatus.OK);
    }

    @GetMapping("/cuisines")
    public ResponseEntity<Map<String, Object>> getRecipesByCuisine(
            @RequestParam(value = "cuisineName") String cuisineName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return new ResponseEntity<>(recipeService
                .getRecipesByCuisine(cuisineName, page, size), HttpStatus.OK);
    }
}
