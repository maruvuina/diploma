package by.bsu.recipebook.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    @NotBlank
    private String authenticationToken;

    @NotBlank
    private String username;

    @NotBlank
    private String refreshToken;

    @NotNull
    private Instant expiresAt;

    @NotNull
    private Integer idUser;

    @Valid
    private List<String> roles;
}
