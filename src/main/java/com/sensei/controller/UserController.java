package com.sensei.controller;

import com.sensei.dto.AuthenticationDto;
import com.sensei.dto.UserDto;
import com.sensei.entity.User;
import com.sensei.exception.*;
import com.sensei.mapper.UserMapper;
import com.sensei.service.UserDbService;
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
public class UserController {

    private final UserDbService userDbService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> fetchAllUsers() {
        List<User> usersList = userDbService.getAllUsers();
        return ResponseEntity.ok(userMapper.mapToUserDtoList(usersList));
    }

    @GetMapping("/usernames")
    public ResponseEntity<List<String>> fetchUsernamesToPreventDuplicate() {
        return ResponseEntity.ok(userDbService.getAllUsernames());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> fetchUserById(@PathVariable Long userId) throws InvalidUserIdException {
        var user = userDbService.findUserById(userId);
        return ResponseEntity.ok(userMapper.mapToUserDto(user));
    }

    @GetMapping("/findWallet")
    public ResponseEntity<Long> findWalledId
            (@RequestParam String username) throws UserNotFoundException {
        var walletId = userDbService.findWalletId(username);
        return ResponseEntity.ok(walletId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDto userDto) throws UserNotVerifyException, WalletAlreadyExistException, InvalidUserIdException {
        var user = userMapper.mapToUser(userDto);
        userDbService.createUser(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/block")
    public ResponseEntity<UserDto> blockUser(@RequestParam Long userId) throws InvalidUserIdException {
        var user = userDbService.findUserById(userId);
        var savedUser = userDbService.blockUser(user);
        return ResponseEntity.ok(userMapper.mapToUserDto(savedUser));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUserData(@Valid @RequestBody UserDto userDto) {
        var user = userMapper.mapToUser(userDto);
        var savedUser = userDbService.updateUser(user);
        return ResponseEntity.ok(userMapper.mapToUserDto(savedUser));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam Long userId) throws InvalidUserIdException, NotEmptyWalletException {
        userDbService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}/notification")
    public ResponseEntity<String> registerToNewCryptocurrencyNotification(@PathVariable Long userId) throws InvalidUserIdException {
        var info = userDbService.registerForNotification(userId);
        return ResponseEntity.ok(info);
    }

    @PutMapping("/{userId}/unsubscribe")
    public ResponseEntity<String> unregisterFromNotification(@PathVariable Long userId) throws InvalidUserIdException {
        var info = userDbService.unregisterForNotification(userId);
        return ResponseEntity.ok(info);
    }
}