package by.bsu.recipebook.config.jwt;

import by.bsu.recipebook.config.service.CustomUserDetails;
import io.jsonwebtoken.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

import static java.util.Date.from;

@Component
public class JwtProvider {
    private static final Logger logger = LogManager.getLogger(JwtProvider.class);

    @Value("$(jwt.secret)")
    private String jwtSecret;

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    public String generateToken(Authentication authentication) {
        Date date = from(Instant.now().plusMillis(jwtExpirationInMillis));
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(from(Instant.now()))
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            logger.log(Level.ERROR, "Token expired");
        } catch (UnsupportedJwtException unsEx) {
            logger.log(Level.ERROR, "Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            logger.log(Level.ERROR, "Malformed jwt");
        } catch (SignatureException sEx) {
            logger.log(Level.ERROR, "Invalid signature");
        } catch (Exception e) {
            logger.log(Level.ERROR, "invalid token");
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String generateTokenWithUserName(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(from(Instant.now()))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .setExpiration(from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}
