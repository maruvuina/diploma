package by.bsu.recipebook.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEmail {
    @NotBlank
    private String subject;

    @NotBlank
    private String recipient;

    @NotBlank
    private String body;
}
