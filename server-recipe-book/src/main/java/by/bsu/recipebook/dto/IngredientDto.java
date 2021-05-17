package by.bsu.recipebook.dto;

import by.bsu.recipebook.validator.transfer.Marker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDto {
    @NotBlank
    private String ingredientName;

    @NotBlank(groups = {Marker.Request.class, Marker.Response.class})
    private String measureAmount;
}
