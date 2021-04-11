package by.bsu.recipebook.controller;

import by.bsu.recipebook.dto.IngredientDto;
import by.bsu.recipebook.service.IngredientService;
import by.bsu.recipebook.validator.transfer.Details;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/ingredients")
@RestController
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    @JsonView({Details.class})
    public ResponseEntity<List<IngredientDto>> getAll() {
        return new ResponseEntity<>(ingredientService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/name")
    @JsonView({Details.class})
    public ResponseEntity<IngredientDto> getIngredientByName(
            @RequestParam(value = "name") String name) {
        return new ResponseEntity<>(ingredientService.getIngredientByName(name), HttpStatus.OK);
    }

    @GetMapping("/name/{pattern}")
    @JsonView({Details.class})
    public ResponseEntity<List<IngredientDto>> getIngredientByIngredientNamePattern(
            @PathVariable String pattern) {
        return new ResponseEntity<>(ingredientService.getByIngredientNamePattern(pattern), HttpStatus.OK);
    }
}
