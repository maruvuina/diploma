package by.bsu.recipebook.dto;

import by.bsu.recipebook.validator.transfer.Details;
import by.bsu.recipebook.validator.transfer.Request;
import by.bsu.recipebook.validator.transfer.Response;
import com.fasterxml.jackson.annotation.JsonView;
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
    @Null(groups = {Request.class})
    @NotNull(groups = {Response.class})
    @JsonView({Details.class})
    private Integer idComment;

    @Null(groups = {Request.class})
    @NotNull(groups = {Response.class})
    @JsonView({Details.class})
    private Integer idUser;

    @Null(groups = {Request.class})
    @NotBlank(groups = {Response.class})
    @JsonView({Details.class})
    private String username;

    @Null(groups = {Request.class})
    @NotBlank(groups = {Response.class})
    @JsonView({Details.class})
    private String createdDate;

    @Null(groups = {Request.class})
    @NotBlank(groups = {Request.class, Response.class})
    @JsonView({Details.class})
    private String content;

    @Nullable
    @JsonView({Details.class})
    private Integer idParent;

    @Valid
    @JsonView({Details.class})
    private List<CommentDto> children = new ArrayList<>();
}
