package by.bsu.recipebook.dto.user;

import by.bsu.recipebook.dto.recipe.RecipeDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGetDto {
    private Integer id;
    private String fullName;
    private List<RecipeDetailsDto> recipeList = new ArrayList<>();
    private String registrationDate;
}
