package by.bsu.recipebook.controller;

import by.bsu.recipebook.dto.CuisineDto;
import by.bsu.recipebook.dto.TagDto;
import by.bsu.recipebook.service.CuisineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/cuisines")
@RestController
@RequiredArgsConstructor
public class CuisineController {
    private final CuisineService cuisineService;

    @GetMapping
    public ResponseEntity<List<CuisineDto>> getAll() {
        return new ResponseEntity<>(cuisineService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> save(@RequestBody @Valid CuisineDto cuisineDto) {
        cuisineService.save(cuisineDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
