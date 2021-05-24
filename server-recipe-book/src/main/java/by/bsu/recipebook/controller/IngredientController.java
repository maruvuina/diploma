package by.bsu.recipebook.controller;

import by.bsu.recipebook.dto.IngredientDto;
import by.bsu.recipebook.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Validated
@RequestMapping("/api/ingredients")
@RestController
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @GetMapping
    public ResponseEntity<List<IngredientDto>> getAll() {
        return new ResponseEntity<>(ingredientService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<IngredientDto> getIngredientByName(
            @RequestParam(value = "name") @NotBlank String name) {
        return new ResponseEntity<>(ingredientService.getIngredientByName(name), HttpStatus.OK);
    }

    @GetMapping("/name/{pattern}")
    public ResponseEntity<List<IngredientDto>> getIngredientByIngredientNamePattern(
            @PathVariable @NotBlank String pattern) {
        return new ResponseEntity<>(ingredientService.getByIngredientNamePattern(pattern), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> save(@RequestBody @Valid IngredientDto ingredientDto) {
        ingredientService.save(ingredientDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
