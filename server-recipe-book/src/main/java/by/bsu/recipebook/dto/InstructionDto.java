package by.bsu.recipebook.dto;

import by.bsu.recipebook.validator.transfer.Details;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructionDto {
    @Positive
    @JsonView({Details.class})
    private int stepNumber;

    @NotBlank
    @JsonView({Details.class})
    private String description;
}
