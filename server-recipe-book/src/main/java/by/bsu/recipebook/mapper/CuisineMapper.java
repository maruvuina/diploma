package by.bsu.recipebook.mapper;

import by.bsu.recipebook.dto.CuisineDto;
import by.bsu.recipebook.entity.Cuisine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CuisineMapper {
    @Mapping(target = "cuisineName", source = "cuisine.cuisineName")
    CuisineDto mapToCuisineDto(Cuisine cuisine);
}
