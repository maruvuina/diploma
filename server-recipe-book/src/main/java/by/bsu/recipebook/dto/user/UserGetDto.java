package by.bsu.recipebook.dto.user;

import by.bsu.recipebook.dto.recipe.RecipeDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGetDto {
    @NotNull
    private Integer id;

    @NotBlank
    private String fullName;

    @NotBlank
    private String email;

    @Valid
    private List<RecipeDetailsDto> recipeList = new ArrayList<>();

    @NotBlank
    private String registrationDate;

    @Valid
    private List<UserDetailsDto> followers = new ArrayList<>();

    @Valid
    private List<UserDetailsDto> followings = new ArrayList<>();
}
