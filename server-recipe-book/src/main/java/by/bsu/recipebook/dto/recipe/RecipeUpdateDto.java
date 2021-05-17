package by.bsu.recipebook.dto.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeUpdateDto {
    private String recipeName;

    private Integer cookingTime;

    private Integer yield;

    private String categoryName;

    private String announce;
}
