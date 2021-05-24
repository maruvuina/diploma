package by.bsu.recipebook.dto;

import by.bsu.recipebook.validator.transfer.Marker;
import lombok.*;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    @Null(groups = {Marker.Request.class})
    @NotNull(groups = {Marker.Response.class})
    private Integer idComment;

    @Null(groups = {Marker.Request.class})
    @NotNull(groups = {Marker.Response.class})
    private Integer idUser;

    @Null(groups = {Marker.Request.class})
    @NotBlank(groups = {Marker.Response.class})
    private String username;

    @Null(groups = {Marker.Request.class})
    @NotBlank(groups = {Marker.Response.class})
    private String createdDate;

    @NotBlank
    private String content;

    @Nullable
    private Integer idParent;

    @Valid
    private List<CommentDto> children = new ArrayList<>();
}
