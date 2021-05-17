package by.bsu.recipebook.dto.user;

import by.bsu.recipebook.validator.transfer.Marker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
    @NotNull
    private Integer id;

    @Null(groups = {Marker.Request.class})
    @NotBlank(groups = {Marker.Response.class})
    private String fullName;
}
