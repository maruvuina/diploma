package by.bsu.recipebook.service;

import by.bsu.recipebook.dto.IngredientDto;
import by.bsu.recipebook.entity.Ingredient;
import by.bsu.recipebook.mapper.IngredientMapper;
import by.bsu.recipebook.repository.IngredientRepository;
import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;


public class IngredientServiceTest {
    private final IngredientRepository ingredientRepository = mock(IngredientRepository.class);

    private final IngredientMapper ingredientMapper = mock(IngredientMapper.class);

    private IngredientService ingredientService;

    @BeforeMethod
    public void setUp() {
        ingredientService = new IngredientService(ingredientRepository, ingredientMapper);
    }

    @AfterMethod
    public void tearDown() {
    }

    @Test
    public void shouldFindIngredientByName() {
        Ingredient ingredient =
                new Ingredient(null, "Закуски", null);
        IngredientDto expectedIngredientDto =
                new IngredientDto(null, "Закуски", null);

        Mockito.when(ingredientRepository.findByIngredientName("Закуски")).thenReturn(ingredient);
        Mockito.when(ingredientMapper.mapToIngredientDto(Mockito.any(Ingredient.class)))
                .thenReturn(expectedIngredientDto);

        IngredientDto actualIngredientResponse =
                ingredientService.getIngredientByName("Закуски");

        Assertions.assertThat(actualIngredientResponse.getIngredientName())
                .isEqualTo(expectedIngredientDto.getIngredientName());
    }

    @Test
    public void shouldFindByIngredientNamePattern() {
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(new Ingredient(null, "Кукурузная мука", null));
        ingredientList.add(new Ingredient(null, "Пшеничная мука", null));
        ingredientList.add(new Ingredient(null, "Рисовая мука", null));

        List<IngredientDto> expectedIngredientDtoList = new ArrayList<>();
        expectedIngredientDtoList.add(new IngredientDto(null, "Кукурузная мука", null));
        expectedIngredientDtoList.add(new IngredientDto(null, "Пшеничная мука", null));
        expectedIngredientDtoList.add(new IngredientDto(null, "Рисовая мука", null));

        Mockito.when(ingredientRepository.searchByIngredientNamePattern("Мука"))
                .thenReturn(ingredientList);
        //Mockito.when(ingredientMapper.mapToIngredientDto(Mockito.any(Ingredient.class)))
        //        .thenReturn(expectedIngredientDtoList);
    }
}
