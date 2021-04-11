package by.bsu.recipebook.dto.recipe;

import by.bsu.recipebook.dto.IngredientDto;
import by.bsu.recipebook.dto.InstructionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipePostDto {
    @NotBlank
    private String recipeName;

    @Positive
    private int cookingTime;

    @Positive
    private int yield;

    @NotBlank
    private String categoryName;

    @NotBlank
    private String announce;

    @Valid
    private List<IngredientDto> ingredients = new ArrayList<>();

    @Valid
    private List<InstructionDto> instructions = new ArrayList<>();
}
