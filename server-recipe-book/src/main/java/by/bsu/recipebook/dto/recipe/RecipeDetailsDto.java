package by.bsu.recipebook.dto.recipe;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDetailsDto {
    @NonNull
    private Integer idRecipe;

    @NotBlank
    private String recipeName;

    @NotBlank
    private String announce;

    @NotBlank
    private String authorName;

    @NonNull
    private Integer likesCount;

    @NonNull
    private Integer idAuthor;
}
