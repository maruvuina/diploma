package by.bsu.recipebook.mapper;

import by.bsu.recipebook.dto.IngredientDto;
import by.bsu.recipebook.entity.Ingredient;
import by.bsu.recipebook.entity.IngredientAmount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    @Mapping(target = "ingredientName", source = "ingredient.ingredientName")
    IngredientDto mapToIngredientDto(Ingredient ingredient);

    @Mappings({
            @Mapping(target = "ingredientName", expression = "java(ingredientAmount.getIngredient().getIngredientName())"),
            @Mapping(target = "measureAmount", source = "ingredientAmount.measureAmount")
    })
    IngredientDto mapToIngredientDto(IngredientAmount ingredientAmount);

    @Mapping(target = "ingredientName", source = "ingredientDto.ingredientName")
    Ingredient mapToIngredient(IngredientDto ingredientDto);
}
