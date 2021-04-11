package by.bsu.recipebook.config.jwt;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    private static final Logger logger = LogManager.getLogger(AuthEntryPointJwt.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException{
        logger.log(Level.ERROR, "Unauthorized error: {}", e.getMessage());
        final String expired = (String) httpServletRequest.getAttribute("expired");
        httpServletResponse
                .sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        Objects.requireNonNullElse(expired, "Invalid Login details"));
    }
}
