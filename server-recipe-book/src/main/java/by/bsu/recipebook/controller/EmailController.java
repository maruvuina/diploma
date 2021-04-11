package by.bsu.recipebook.controller;

import by.bsu.recipebook.dto.EmailDto;
import by.bsu.recipebook.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/contact")
@RequiredArgsConstructor
@RestController
public class EmailController {
    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<Void> sendMessage(@RequestBody @Valid EmailDto emailDto) {
        emailService.sendMessageToCompany(emailDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
