package by.bsu.recipebook.mapper;

import by.bsu.recipebook.dto.CommentDto;
import by.bsu.recipebook.entity.Comment;
import by.bsu.recipebook.entity.Recipe;
import by.bsu.recipebook.entity.User;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
    @Mappings({
            @Mapping(target = "content", source = "commentDto.content"),
            @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())"),
            @Mapping(target = "user", source = "user"),
            @Mapping(target = "recipe", source = "recipe")
    })
    public abstract Comment mapToComment(CommentDto commentDto, User user, Recipe recipe);

    @Mappings({
            @Mapping(target = "idComment", source = "comment.idComment"),
            @Mapping(target = "idUser", expression = "java(comment.getUser().getIdUser())"),
            @Mapping(target = "username", expression = "java(comment.getUser().getFullName())"),
            @Mapping(target = "createdDate", expression = "java(getCreatedDate(comment))"),
            @Mapping(target = "content", expression = "java(comment.getContent())"),
            @Mapping(target = "idParent", ignore = true),
            @Mapping(target = "children", expression = "java(getCommentChildren(comment))")
    })
    public abstract CommentDto mapToCommentDto(Comment comment);

    @AfterMapping
    void setIdParent(Comment comment, @MappingTarget CommentDto commentDto) {
        if (comment.getParent() == null) {
            commentDto.setIdParent(null);
        } else {
            commentDto.setIdParent(comment.getParent().getIdComment());
        }
    }

    String getCreatedDate(Comment comment) {
        return TimeAgo.using(comment.getCreatedDate().toEpochMilli());
    }

    List<CommentDto> getCommentChildren(Comment comment) {
        return comment.getChildren()
                .stream()
                .map(this::mapToCommentDto)
                .collect(Collectors.toList());
    }
}
