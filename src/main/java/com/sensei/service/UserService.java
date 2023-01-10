package com.sensei.service;

import com.sensei.entity.User;
import com.sensei.entity.WalletCrypto;
import com.sensei.exception.*;
import com.sensei.mapper.UserMapper;
import com.sensei.repository.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;
    private final CryptocurrencyService cryptoService;
    private final WalletService walletService;

    public User findUserById(Long id) throws InvalidUserIdException {
        return userDao.findById(id).orElseThrow(InvalidUserIdException::new);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public User createUser(User user) throws UserNotVerifyException, WalletAlreadyExistException, InvalidUserIdException {
        user.setActive(true);
        user.setDateOfJoin(LocalDateTime.now());
        var createdUser = userDao.save(user);
        if (user.getPESEL() != null && user.getIdCard() != null) {
            walletService.createWallet(createdUser.getId());
        }
        return createdUser;
    }

    public User updateUser(User user) {
        return userDao.save(user);
    }

    public User blockUser(User user) {
        user.setActive(!user.isActive());
        return userDao.save(user);
    }

    public void deleteUser(Long id) throws InvalidUserIdException, NotEmptyWalletException {
        User user = userDao.findById(id).orElseThrow(InvalidUserIdException::new);
        List<WalletCrypto> filteredList = user.getWallet().getCryptosList().stream()
                .filter(n -> n.getQuantity().doubleValue() != 0.00)
                .collect(Collectors.toList());
        if (!filteredList.isEmpty()) {
            throw new NotEmptyWalletException();
        }
        userDao.deleteById(id);
    }

    public String registerForNotification(Long userId) throws InvalidUserIdException {
        var user = userDao.findById(userId).orElseThrow(InvalidUserIdException::new);
        user.setNotification(true);
        var userDto = userMapper.mapToUserDto(user);
        userDao.save(user);
        cryptoService.registryObserver(userDto);
        log.info("User " + userDto.getId() + " added to notification list");
        return "User " + userDto.getUsername() + " added to notification list";
    }

    public String unregisterForNotification(Long userId) throws InvalidUserIdException {
        var user = userDao.findById(userId).orElseThrow(InvalidUserIdException::new);
        user.setNotification(false);
        var userDto = userMapper.mapToUserDto(user);
        userDao.save(user);
        cryptoService.removeObserver(userDto);
        log.info("Notification for: " + userDto.getId() + " has been stopped.");
        return "Notification for: " + userDto.getUsername() + " has been stopped.";
    }

    public List<String> getAllUsernames() {
        return userDao.findAll().stream()
                .map(user -> user.getUsername())
                .collect(Collectors.toList());
    }

    public Long findWalletId(String username) throws UserNotFoundException {
        var user = userDao.findByUsername(username).orElseThrow(UserNotFoundException::new);
        return user.getWallet().getId();
    }
}
