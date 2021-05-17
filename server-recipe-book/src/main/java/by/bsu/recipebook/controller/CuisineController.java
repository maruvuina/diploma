package by.bsu.recipebook.controller;

import by.bsu.recipebook.dto.CuisineDto;
import by.bsu.recipebook.service.CuisineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RequestMapping("/api/cuisines")
@RestController
@RequiredArgsConstructor
public class CuisineController {
    private final CuisineService cuisineService;

    @GetMapping
    public ResponseEntity<List<CuisineDto>> getAll() {
        return new ResponseEntity<>(cuisineService.getAll(), HttpStatus.OK);
    }
}
