package by.bsu.recipebook.service;

import by.bsu.recipebook.dto.IngredientDto;
import by.bsu.recipebook.mapper.IngredientMapper;
import by.bsu.recipebook.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    private final IngredientMapper ingredientMapper;

    @Transactional(readOnly = true)
    public List<IngredientDto> getAll() {
        return ingredientRepository.findAll()
                .stream()
                .map(ingredientMapper::mapToIngredientDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public IngredientDto getIngredientByName(String ingredientName) {
        return ingredientMapper
                .mapToIngredientDto(ingredientRepository
                        .findByIngredientName(ingredientName));
    }

    @Transactional(readOnly = true)
    public List<IngredientDto> getByIngredientNamePattern(String pattern) {
        return ingredientRepository.searchByIngredientNamePattern(pattern)
                .stream()
                .map(ingredientMapper::mapToIngredientDto)
                .collect(Collectors.toList());
    }

    public void save(IngredientDto ingredientDto) {
        ingredientRepository.save(ingredientRepository.findByIngredientName(ingredientDto.getIngredientName()));
    }
}
