package by.bsu.recipebook.controller;

import by.bsu.recipebook.dto.auth.AuthenticationResponse;
import by.bsu.recipebook.dto.auth.LoginRequest;
import by.bsu.recipebook.dto.auth.RefreshTokenRequest;
import by.bsu.recipebook.dto.auth.RegisterRequest;
import by.bsu.recipebook.exception.ServiceException;
import by.bsu.recipebook.service.AuthService;
import by.bsu.recipebook.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<Boolean> signup(@RequestBody @Valid RegisterRequest registerRequest)
            throws ServiceException {
        return new ResponseEntity<>(authService.signup(registerRequest), HttpStatus.CREATED);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) throws ServiceException {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<Boolean> isUserActive(
            @RequestParam(value = "username") @NotBlank String username) {
        return new ResponseEntity<>(authService.isUserActive(username), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/refresh/token")
    public ResponseEntity<AuthenticationResponse> refreshTokens(
            @Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws ServiceException {
        return new ResponseEntity<>(authService.refreshToken(refreshTokenRequest), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return new ResponseEntity<>("Refresh Token Deleted Successfully.",
                HttpStatus.OK);
    }
}
