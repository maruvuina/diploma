package by.bsu.recipebook.controller;

import by.bsu.recipebook.dto.TagDto;
import by.bsu.recipebook.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RequestMapping("/api/tags")
@RestController
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagDto>> getAll() {
        return new ResponseEntity<>(tagService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> save(@RequestBody @Valid TagDto tagDto) {
        tagService.save(tagDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
