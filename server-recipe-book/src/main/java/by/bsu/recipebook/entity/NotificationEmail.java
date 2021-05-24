package by.bsu.recipebook.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEmail {
    @NotBlank(message = "Please check subject.")
    private String subject;

    @NotBlank(message = "Please check recipient.")
    private String recipient;

    @NotBlank(message = "Please check body.")
    private String body;
}
