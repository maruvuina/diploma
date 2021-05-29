package by.bsu.recipebook.controller;

import by.bsu.recipebook.dto.user.UserDetailsDto;
import by.bsu.recipebook.dto.user.UserGetDto;
import by.bsu.recipebook.exception.ServiceException;
import by.bsu.recipebook.service.UserService;
import by.bsu.recipebook.validator.transfer.Marker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/image/{id}")
    public ResponseEntity<String> getUserAvatar(@PathVariable("id") @Min(1) int id) throws ServiceException {
        return new ResponseEntity<>(userService.getUserAvatar(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGetDto> getUserById(@PathVariable("id") @Min(1) int id) throws ServiceException {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/subscribe/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> subscribe(@PathVariable("id") @Min(1) int idUser,
        @RequestBody @Validated({Marker.Request.class}) UserDetailsDto userDetailsDto) throws ServiceException {
        userService.subscribe(idUser, userDetailsDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/followings/{id}")
    public ResponseEntity<Map<String, Object>> getFollowings(
            @PathVariable("id") @Min(1) int id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return new ResponseEntity<>(userService.getFollowings(id, page, size),
                HttpStatus.OK);
    }

    @GetMapping("/followers/{id}")
    public ResponseEntity<Map<String, Object>> getFollowers(
            @PathVariable("id") @Min(1) int id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return new ResponseEntity<>(userService.getFollowers(id, page, size),
                HttpStatus.OK);
    }

    @PostMapping(path = "/{id}", consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> update(@RequestPart(value = "file", required = false)
        @RequestParam(value = "userDto", required = false) @NotBlank String jsonUserDto,
        @NotNull MultipartFile file,
        @PathVariable("id") @Min(1) int id) throws ServiceException {
        userService.update(jsonUserDto, file, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/subscribe/{idFollowing}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Boolean> isSubscribed(@PathVariable("idFollowing") @Min(1) int idFollowing) throws ServiceException {
        return new ResponseEntity<>(userService.isSubscribed(idFollowing), HttpStatus.OK);
    }

    @GetMapping("/mailing")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Boolean> isMailing() {
        return new ResponseEntity<>(userService.isMailing(), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return new ResponseEntity<>(userService.getAll(page, size), HttpStatus.OK);
    }
}
