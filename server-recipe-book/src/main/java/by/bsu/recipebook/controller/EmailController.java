package by.bsu.recipebook.controller;

import by.bsu.recipebook.dto.EmailDto;
import by.bsu.recipebook.service.email.GmailService;
import by.bsu.recipebook.service.email.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RequestMapping("/api/sending")
@RequiredArgsConstructor
@RestController
public class EmailController {
    private final GmailService gmailService;

    private final MailService mailService;

    @PostMapping("/contact")
    public ResponseEntity<Void> sendMessage(@RequestBody @Valid EmailDto emailDto) {
        gmailService.sendMessageToCompany(emailDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @PostMapping("/mailing")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> subscribeToNewsletter(@RequestBody @Valid EmailDto emailDto) {
        mailService.subscribeToNewsletter(emailDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @PostMapping("/unmailing")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> unsubscribeToNewsletter(@RequestBody @Valid EmailDto emailDto) {
        mailService.unsubscribeToNewsletter(emailDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
