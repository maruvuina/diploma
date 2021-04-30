package by.bsu.recipebook.controller;

import by.bsu.recipebook.dto.TagDto;
import by.bsu.recipebook.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/tags")
@RestController
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagDto>> getAll() {
        return new ResponseEntity<>(tagService.getAll(), HttpStatus.OK);
    }
}
