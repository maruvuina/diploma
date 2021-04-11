package by.bsu.recipebook.dto.auth;

import by.bsu.recipebook.entity.Role;
import by.bsu.recipebook.validator.ParamRegex;
import by.bsu.recipebook.validator.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull
    @Email(regexp = ParamRegex.EMAIL_REGEX)
    private String email;

    @ValidPassword
    private String password;

    @NotBlank
    private String fullName;

    private Set<Role> roles;
}
