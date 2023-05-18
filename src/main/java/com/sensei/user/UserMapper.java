package com.sensei.user;

import com.sensei.user.UserDto;
import com.sensei.user.User;
import com.sensei.user.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public User mapToUser(UserDto userDto) {
        User user;
        Long id = userDto.getId();
        if (id != null) {
            user = userDao.findById(id).orElse(new User());
        } else {
            user = new User();
        }
        user.setId(id);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPESEL(userDto.getPESEL());
        user.setIdCard(userDto.getIdCard());
        user.setDateOfJoin(userDto.getDateOfJoin());
        user.setEmail(userDto.getEmail());
        user.setActive(userDto.isActive());
        user.setNotification(userDto.isNotification());
        return user;
    }

    public UserDto mapToUserDto(User user) {
        Long walletId = null;
        if(user.getWallet() != null) {
            walletId = user.getWallet().getId();
        }
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .password(user.getPassword())
                .PESEL(user.getPESEL())
                .idCard(user.getIdCard())
                .dateOfJoin(user.getDateOfJoin())
                .email(user.getEmail())
                .active(user.isActive())
                .walletId(walletId)
                .notification(user.isNotification())
                .build();
    }

    public List<UserDto> mapToUserDtoList(List<User> usersList) {
        return usersList.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }
}
