package by.bsu.recipebook.controller;

import by.bsu.recipebook.dto.user.UserDetailsDto;
import by.bsu.recipebook.dto.user.UserDto;
import by.bsu.recipebook.dto.user.UserGetDto;
import by.bsu.recipebook.exception.ServiceException;
import by.bsu.recipebook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/image/{id}")
    public ResponseEntity<String> getUserAvatar(@PathVariable("id") int id) {
        return new ResponseEntity<>(userService.getUserAvatar(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGetDto> getUserById(@PathVariable("id") int id) throws ServiceException {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/subscribe/{id}")
    public ResponseEntity<Object> subscribe(@PathVariable("id") int idUser,
        @RequestBody @Valid UserDto userDto) throws ServiceException {
        userService.subscribe(idUser, userDto.getId());
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @DeleteMapping("/unsubscribe/{id}")
    public ResponseEntity<Object> unsubscribe(@PathVariable("id") int idUser) {
        userService.unsubscribe(idUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/following/{id}")
    public ResponseEntity<List<UserDetailsDto>> getFollowings(
            @PathVariable("id") int id) {
        return new ResponseEntity<>(userService.getFollowings(id),
                HttpStatus.OK);
    }

    @GetMapping("/follower/{id}")
    public ResponseEntity<List<UserDetailsDto>> getFollowers(
            @PathVariable("id") int id) {
        return new ResponseEntity<>(userService.getFollowers(id),
                HttpStatus.OK);
    }

    @PostMapping(path = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Void> update(@RequestPart(value = "file", required = false)
        @RequestParam(value = "userDto", required = false) @Valid @NotNull String jsonUserDto,
        @Valid @NotNull @NotBlank MultipartFile file,
        @PathVariable("id") int id) throws ServiceException {
        userService.update(jsonUserDto, file, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
