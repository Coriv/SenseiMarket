package com.sensei.mapper;

import com.sensei.domain.UserDto;
import com.sensei.entity.User;
import com.sensei.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final UserDao userDao;

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
        user.setPassword(userDto.getPassword());
        user.setPESEL(userDto.getPESEL());
        user.setIdCard(userDto.getIdCard());
        user.setDateOfJoin(userDto.getDateOfJoin());
        user.setEmail(userDto.getEmail());
        user.setActive(userDto.isActive());
        return user;
    }

    public static UserDto mapToUserDto(User user) {
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
                .isActive(user.isActive())
                .walletId(user.getWallet().getId())
                .build();
    }

    public List<UserDto> mapToUserDtoList(List<User> usersList) {
        return usersList.stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }
}
