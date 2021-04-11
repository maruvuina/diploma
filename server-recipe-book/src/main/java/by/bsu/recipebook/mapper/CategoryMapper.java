package by.bsu.recipebook.mapper;

import by.bsu.recipebook.dto.CategoryDto;
import by.bsu.recipebook.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "categoryName", source = "category.categoryName")
    CategoryDto mapToCategoryDto(Category category);
}
