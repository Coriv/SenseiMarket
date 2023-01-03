package com.sensei.controller;

import com.sensei.dto.UserDto;
import com.sensei.entity.User;
import com.sensei.exception.InvalidUserIdException;
import com.sensei.exception.NotEmptyWalletException;
import com.sensei.mapper.UserMapper;
import com.sensei.service.UserDbService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserDbService userDbService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> fetchAllUsers() {
        List<User> usersList = userDbService.getAllUsers();
        return ResponseEntity.ok(userMapper.mapToUserDtoList(usersList));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> fetchUserById(@PathVariable Long userId) throws InvalidUserIdException {
        var user = userDbService.findUserById(userId);
        return ResponseEntity.ok(userMapper.mapToUserDto(user));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDto userDto) {
        var user = userMapper.mapToUser(userDto);
        userDbService.save(user);
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
        var savedUser = userDbService.save(user);
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