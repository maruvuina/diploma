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

@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid RegisterRequest registerRequest)
            throws ServiceException {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Successful",
                HttpStatus.OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) throws ServiceException {
        System.out.println("token--> " + token);
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
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
