package by.bsu.recipebook.mapper;

import by.bsu.recipebook.dto.user.UserDetailsDto;
import by.bsu.recipebook.entity.Followers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface FollowersMapper {
    @Mappings({
            @Mapping(target = "id", expression = "java(followers.getFrom().getIdUser())"),
            @Mapping(target = "fullName", expression = "java(followers.getFrom().getFullName())")
    })
    UserDetailsDto mapToFollower(Followers followers);

    @Mappings({
            @Mapping(target = "id", expression = "java(followers.getTo().getIdUser())"),
            @Mapping(target = "fullName", expression = "java(followers.getTo().getFullName())")
    })
    UserDetailsDto mapToFollowing(Followers followers);
}
