package by.bsu.recipebook.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class IngredientList {
    public List<IngredientDto> ingredientDtoList = new ArrayList<>();
}
