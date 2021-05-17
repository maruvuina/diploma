package by.bsu.recipebook.dto.recipe;

import by.bsu.recipebook.entity.LikeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeLikeDto {
    @NotNull
    private Integer idRecipe;

    @NotNull
    private LikeType likeType;
}
