package by.bsu.recipebook.dto;

import by.bsu.recipebook.validator.transfer.Details;
import by.bsu.recipebook.validator.transfer.Request;
import by.bsu.recipebook.validator.transfer.Response;
import by.bsu.recipebook.validator.transfer.Show;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDto {
    //@Null(groups = {Request.class, Response.class})
    //@NotNull(groups = {Show.class})
    //@JsonView({Details.class})
    private Integer idIngredient;

    //@NotBlank(groups = {Request.class, Response.class, Show.class})
    //@JsonView({Details.class})
    private String ingredientName;

    //@NotBlank(groups = {Request.class, Response.class})
    //@JsonView({Details.class})
    private String measureAmount;
}
