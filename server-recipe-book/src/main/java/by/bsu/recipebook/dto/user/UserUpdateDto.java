package by.bsu.recipebook.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UserUpdateDto {
    @NotBlank
    private String fullName;
}
