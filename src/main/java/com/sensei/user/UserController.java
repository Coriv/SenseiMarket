package com.sensei.user;

import com.sensei.exception.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<List<UserDto>> fetchAllUsers() {
        List<User> usersList = userService.getAllUsers();
        return ResponseEntity.ok(userMapper.mapToUserDtoList(usersList));
    }

    @GetMapping("/usernames")
    public ResponseEntity<List<String>> fetchUsernamesToPreventDuplicate() {
        return ResponseEntity.ok(userService.getAllUsernames());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> fetchUserById(@PathVariable Long userId) throws InvalidUserIdException {
        var user = userService.findUserById(userId);
        return ResponseEntity.ok(userMapper.mapToUserDto(user));
    }

    @GetMapping("/findWallet")
    public ResponseEntity<Long> findWalledId(
            @RequestParam String username) throws UserNotFoundException {
        var walletId = userService.findWalletId(username);
        return ResponseEntity.ok(walletId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> createUser(
            @Valid @RequestBody UserDto userDto) throws UserNotVerifyException, WalletAlreadyExistException, InvalidUserIdException {
        var user = userMapper.mapToUser(userDto);
        var token = userService.createUser(user);
        return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .build());
    }

    @PostMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthDto authDto) {
        return ResponseEntity.ok(authService.authenticate(authDto));
    }

    @PutMapping("/block")
    public ResponseEntity<UserDto> blockUser(@RequestParam Long userId) throws InvalidUserIdException {
        var user = userService.findUserById(userId);
        var savedUser = userService.blockUser(user);
        return ResponseEntity.ok(userMapper.mapToUserDto(savedUser));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUserData(@Valid @RequestBody UserDto userDto) {
        var user = userMapper.mapToUser(userDto);
        var savedUser = userService.updateUser(user);
        return ResponseEntity.ok(userMapper.mapToUserDto(savedUser));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam Long userId) throws InvalidUserIdException, NotEmptyWalletException {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}/notification")
    public ResponseEntity<String> registerToNewCryptocurrencyNotification(@PathVariable Long userId) throws InvalidUserIdException {
        var info = userService.registerForNotification(userId);
        return ResponseEntity.ok(info);
    }

    @PutMapping("/{userId}/unsubscribe")
    public ResponseEntity<String> unregisterFromNotification(@PathVariable Long userId) throws InvalidUserIdException {
        var info = userService.unregisterForNotification(userId);
        return ResponseEntity.ok(info);
    }
}