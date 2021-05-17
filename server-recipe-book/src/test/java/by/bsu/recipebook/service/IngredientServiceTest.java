package by.bsu.recipebook.service;

import by.bsu.recipebook.dto.IngredientDto;
import by.bsu.recipebook.entity.Ingredient;
import by.bsu.recipebook.mapper.IngredientMapper;
import by.bsu.recipebook.repository.IngredientRepository;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class IngredientServiceTest {
    private AutoCloseable closeable;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientMapper ingredientMapper;

    private IngredientService ingredientService;

    @BeforeMethod
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        ingredientService = new IngredientService(ingredientRepository, ingredientMapper);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void shouldFindIngredientByName() {
        Ingredient ingredient =
                new Ingredient(null, "Закуски", null);
        IngredientDto expectedIngredientDto =
                new IngredientDto("Закуски", null);
        when(ingredientRepository.findByIngredientName("Закуски")).thenReturn(ingredient);
        when(ingredientMapper.mapToIngredientDto(any(Ingredient.class)))
                .thenReturn(expectedIngredientDto);
        IngredientDto actualIngredient =
                ingredientService.getIngredientByName("Закуски");
        assertThat(actualIngredient.getIngredientName())
                .isEqualTo(expectedIngredientDto.getIngredientName());
    }

    @Test
    public void shouldFindByIngredientNamePattern() {
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(new Ingredient(null, "Кукурузная мука", null));
        List<IngredientDto> expectedIngredientDtoList = new ArrayList<>();
        expectedIngredientDtoList.add(new IngredientDto("Кукурузная мука", null));
        when(ingredientRepository.searchByIngredientNamePattern("Мука"))
                .thenReturn(ingredientList);
        when(ingredientMapper.mapToIngredientDto(any(Ingredient.class)))
                .thenReturn(new IngredientDto("Кукурузная мука", null));
        List<IngredientDto> actualIngredientDtoList = ingredientService.getByIngredientNamePattern("Мука");
        assertThat(actualIngredientDtoList).isEqualTo(expectedIngredientDtoList);
    }
}
