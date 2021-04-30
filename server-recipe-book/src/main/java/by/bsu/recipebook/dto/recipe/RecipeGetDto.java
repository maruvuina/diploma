package by.bsu.recipebook.dto.recipe;

import by.bsu.recipebook.dto.CommentDto;
import by.bsu.recipebook.dto.IngredientDto;
import by.bsu.recipebook.dto.InstructionDto;
import by.bsu.recipebook.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeGetDto {
    @NotNull
    private Integer idRecipe;

    @NotBlank
    private String recipeName;

    @Positive
    private int cookingTime;

    @Positive
    private int yield;

    @NotBlank
    private String createdDate;

    @NotBlank
    private String authorName;

    @NotBlank
    private String announce;

    @NotBlank
    private String categoryName;

    @Valid
    private List<IngredientDto> ingredients = new ArrayList<>();

    @Valid
    private List<InstructionDto> instructions = new ArrayList<>();

    @Valid
    private List<CommentDto> comments = new ArrayList<>();

    @Valid
    private List<TagDto> tags = new ArrayList<>();

    @NotNull
    private Integer likesCount;
}
