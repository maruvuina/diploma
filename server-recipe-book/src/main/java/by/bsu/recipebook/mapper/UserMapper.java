package by.bsu.recipebook.mapper;

import by.bsu.recipebook.dto.auth.RegisterRequest;
import by.bsu.recipebook.dto.recipe.RecipeDetailsDto;
import by.bsu.recipebook.dto.user.UserDetailsDto;
import by.bsu.recipebook.dto.user.UserGetDto;
import by.bsu.recipebook.entity.User;
import by.bsu.recipebook.util.FormatterPattern;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Autowired
    private RecipeMapper recipeMapper;

    @Autowired
    private FollowersMapper followersMapper;

    @Mapping(target = "email", source = "registerRequest.email")
    @Mapping(target = "password", source = "registerRequest.password")
    @Mapping(target = "fullName", source = "registerRequest.fullName")
    @Mapping(target = "roles", source = "registerRequest.roles")
    @Mapping(target = "avatarLocation", expression = "java(by.bsu.recipebook.constants.Constants.DEFAULT_AVATAR_LOCATION)")
    @Mapping(target = "registrationDate", expression = "java(java.time.Instant.now())")
    public abstract User mapToUser(RegisterRequest registerRequest);

    @Mapping(target = "id", source = "user.idUser")
    @Mapping(target = "fullName", source = "user.fullName")
    @Mapping(target = "recipeList", expression = "java(getRecipeList(user))")
    @Mapping(target = "registrationDate", expression = "java(getRegistrationDate(user))")
    @Mapping(target = "followers", expression = "java(getFollowers(user))")
    @Mapping(target = "followings", expression = "java(getFollowings(user))")
    public abstract UserGetDto mapToUserGetDto(User user);

    List<UserDetailsDto> getFollowers(User user) {
        return user.getFollowers()
                .stream()
                .map(followersMapper::mapToFollower)
                .collect(Collectors.toList());
    }

    List<UserDetailsDto> getFollowings(User user) {
        return user.getFollowing()
                .stream()
                .map(followersMapper::mapToFollowing)
                .collect(Collectors.toList());
    }

    List<RecipeDetailsDto> getRecipeList(User user) {
        return user.getRecipes()
                .stream()
                .map(recipeMapper::mapToRecipeDetailsDto)
                .collect(Collectors.toList());
    }

    String getRegistrationDate(User user) {
        return FormatterPattern
                .getFormatterPatternToDisplay()
                .format(user.getRegistrationDate());
    }

    @Mapping(target = "id", source = "user.idUser")
    @Mapping(target = "fullName", source = "user.fullName")
    public abstract UserDetailsDto mapToUserDetailsDto(User user);
}
