package by.bsu.recipebook.service;

import by.bsu.recipebook.constants.Constants;
import by.bsu.recipebook.dto.user.UserDetailsDto;
import by.bsu.recipebook.dto.user.UserGetDto;
import by.bsu.recipebook.dto.user.UserUpdateDto;
import by.bsu.recipebook.entity.*;
import by.bsu.recipebook.exception.ServiceException;
import by.bsu.recipebook.mapper.JsonMapper;
import by.bsu.recipebook.mapper.MapResponse;
import by.bsu.recipebook.mapper.UserMapper;
import by.bsu.recipebook.repository.FollowersRepository;
import by.bsu.recipebook.repository.UserRepository;
import by.bsu.recipebook.service.email.MailService;
import by.bsu.recipebook.util.FormatterPattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final FollowersRepository followersRepository;

    private final JsonMapper jsonMapper;

    private final MailService mailService;

    private final AuthService authService;

    public String getUserAvatar(int id) {
        String userAvatar = null;
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            String avatarLocation = userOptional.get().getAvatarLocation();
            userAvatar = ImageService.get(avatarLocation);
        }
        return userAvatar;
    }

    @Transactional(readOnly = true)
    public UserGetDto getById(int id) throws ServiceException {
        User user = getUserById(id);
        return userMapper.mapToUserGetDto(user);
    }

    @Transactional
    public void subscribe(int idUser, UserDetailsDto userDetailsDto) throws ServiceException {
        User user = getUserById(idUser);
        User following = getUserById(userDetailsDto.getId());
        Optional<Followers> followersOptional =
                followersRepository.findByFromAndTo(user, following);
        if (followersOptional.isEmpty()) {
            followersRepository.save(new Followers(user, following));
            String message = FormatterPattern
                    .getFormatterPatternToDisplay()
                    .format(Instant.now()) + " " + user.getFullName() + " became your follower.";
            mailService.sendMail(new NotificationEmail("New follower on Recipe Book",
                    following.getEmail(), message));
        } else {
            Followers followers = followersOptional.get();
            followers.setSubscribed(!followers.isSubscribed());
            followersRepository.save(followers);
        }
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getFollowings(int idUser, int page, int size) {
        Page<Followers> followingsPage = followersRepository.findFollowings(idUser, PageRequest.of(page, size));
        List<UserDetailsDto> userDetailsDtoList = getFollowingsDetailsDto(followingsPage);
        return MapResponse.getResponseAsMap("followings", userDetailsDtoList, followingsPage);
    }

    private List<UserDetailsDto> getFollowingsDetailsDto(Page<Followers> pageTuts) {
        return pageTuts
                .getContent()
                .stream()
                .map(followers -> userMapper.mapToUserDetailsDto(followers.getTo()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getFollowers(int idUser, int page, int size) {
        Page<Followers> followersPage = followersRepository.findFollowers(idUser, PageRequest.of(page, size));
        List<UserDetailsDto> userDetailsDtoList = getFollowersDetailsDto(followersPage);
        return MapResponse.getResponseAsMap("followers", userDetailsDtoList, followersPage);
    }

    private List<UserDetailsDto> getFollowersDetailsDto(Page<Followers> pageTuts) {
        return pageTuts
                .getContent()
                .stream()
                .map(followers -> userMapper.mapToUserDetailsDto(followers.getFrom()))
                .collect(Collectors.toList());
    }

    private User getUserById(int id) throws ServiceException {
        return userRepository
                .findById(id)
                .orElseThrow(
                        () -> new ServiceException(
                                "Error occurs while trying to get user with such " + id));
    }

    @Transactional
    public void update(String jsonUserDto, MultipartFile file, int id) throws ServiceException {
        User user = getUserById(id);
        UserUpdateDto userUpdateDto = jsonMapper.mapDto(jsonUserDto, UserUpdateDto.class);
        String fullName = userUpdateDto.getFullName();
        if (!fullName.isBlank()) {
            user.setFullName(fullName);
        }
        if (file != null) {
            String avatarLocation = ImageService.save(file, Constants.USER_UPLOAD_IMAGES_DIR);
            user.setAvatarLocation(avatarLocation);
        }
        userRepository.save(user);
    }

    @Transactional
    public boolean isSubscribed(int idFollowing) throws ServiceException {
        User following = getUserById(idFollowing);
        Followers followers = followersRepository
                .isSubscribed(authService.getCurrentUser().getIdUser(),
                        following.getIdUser());
        System.out.println(followers);
        return followers != null;
    }
}
