package by.bsu.recipebook.service;

import by.bsu.recipebook.dto.CategoryDto;
import by.bsu.recipebook.entity.Category;
import by.bsu.recipebook.mapper.CategoryMapper;
import by.bsu.recipebook.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryDto> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::mapToCategoryDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(CategoryDto categoryDto) {
        Category category = categoryRepository
                .findByCategoryName(categoryDto.getCategoryName());
        categoryRepository.save(category);
    }
}
