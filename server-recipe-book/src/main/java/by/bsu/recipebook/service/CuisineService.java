package by.bsu.recipebook.service;

import by.bsu.recipebook.dto.CuisineDto;
import by.bsu.recipebook.mapper.CuisineMapper;
import by.bsu.recipebook.repository.CuisineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CuisineService {
    private final CuisineRepository cuisineRepository;

    private final CuisineMapper cuisineMapper;

    @Transactional(readOnly = true)
    public List<CuisineDto> getAll() {
        return cuisineRepository.findAll()
                .stream()
                .map(cuisineMapper::mapToCuisineDto)
                .collect(Collectors.toList());
    }
}
