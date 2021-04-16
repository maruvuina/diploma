package by.bsu.recipebook.service;

import by.bsu.recipebook.entity.RefreshToken;
import by.bsu.recipebook.repository.RefreshTokenRepository;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RefreshTokenServiceTest {
    private AutoCloseable closeable;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    private RefreshTokenService refreshTokenService;

    private String token;

    @BeforeMethod
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        refreshTokenService = new RefreshTokenService(refreshTokenRepository);
        token = UUID.randomUUID().toString();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void shouldGenerateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken(null, token, null);
        RefreshToken expectedRefreshToken = new RefreshToken(1, token, Instant.now());
        when(refreshTokenRepository.save(any(RefreshToken.class))).thenReturn(refreshToken);
        RefreshToken actualRefreshToken = refreshTokenService.generateRefreshToken();
        assertThat(actualRefreshToken.getToken()).isEqualTo(expectedRefreshToken.getToken());
    }

    @Test()
    public void shouldValidateRefreshToken() {
        when(refreshTokenRepository.existsByToken(token)).thenReturn(true);
        assertThat(refreshTokenService.validateRefreshToken(token)).isTrue();
    }

    @Test()
    public void shouldDeleteRefreshToken() {
        doNothing().when(refreshTokenRepository).deleteByToken(token);
        refreshTokenService.deleteRefreshToken(token);
        verify(refreshTokenRepository, times(1))
                .deleteByToken(token);
    }
}
