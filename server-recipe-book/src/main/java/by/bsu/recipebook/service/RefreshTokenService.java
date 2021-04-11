package by.bsu.recipebook.service;

import by.bsu.recipebook.entity.RefreshToken;
import by.bsu.recipebook.exception.ServiceException;
import by.bsu.recipebook.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());
        return refreshTokenRepository.save(refreshToken);
    }

    public void validateRefreshToken(String token) throws ServiceException {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new ServiceException("Invalid refresh Token"));
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
