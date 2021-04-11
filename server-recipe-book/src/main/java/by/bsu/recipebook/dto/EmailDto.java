package by.bsu.recipebook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    @NotBlank
    private String fromEmail;

    @NotBlank
    private String title;

    @NotBlank
    private String message;
}
