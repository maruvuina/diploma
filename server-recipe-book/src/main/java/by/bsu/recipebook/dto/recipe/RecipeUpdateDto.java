package by.bsu.recipebook.dto.recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class RecipeUpdateDto {
    @NotNull
    private final JsonNullable<String> recipeName = JsonNullable.undefined();

    @NotNull
    private final JsonNullable<Integer> cookingTime = JsonNullable.undefined();

    @NotNull
    private final JsonNullable<Integer> yield = JsonNullable.undefined();

    @NotBlank
    private final JsonNullable<String> announce = JsonNullable.undefined();

    @NotNull
    private final JsonNullable<String> categoryName = JsonNullable.undefined();
}
