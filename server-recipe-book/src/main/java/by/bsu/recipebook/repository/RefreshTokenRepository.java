package by.bsu.recipebook.repository;

import by.bsu.recipebook.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    void deleteByToken(String token);

    boolean existsByToken(String token);
}
