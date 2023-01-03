package com.sensei.service;

import com.sensei.entity.User;
import com.sensei.entity.WalletCrypto;
import com.sensei.exception.InvalidUserIdException;
import com.sensei.exception.NotEmptyWalletException;
import com.sensei.mapper.UserMapper;
import com.sensei.repository.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDbService {

    private final UserDao userDao;
    private final UserMapper userMapper;
    private final CryptocurrencyDbService cryptoService;
    public User findUserById(Long id) throws InvalidUserIdException {
        return userDao.findById(id).orElseThrow(InvalidUserIdException::new);
    }

    public List<User> getAllUsers() {
       return userDao.findAll();
    }

    public User save(User User) {
        return userDao.save(User);
    }

    public User blockUser(User User) {
        User.setActive(!User.isActive());
        return userDao.save(User);
    }

    public void deleteUser(Long id) throws InvalidUserIdException, NotEmptyWalletException {
        User User = userDao.findById(id).orElseThrow(InvalidUserIdException::new);
        List<WalletCrypto> filteredList = User.getWallet().getCryptosList().stream()
                        .filter(n  -> n.getQuantity().doubleValue() != 0.00)
                                .collect(Collectors.toList());
        if(!filteredList.isEmpty()) {
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
        return "Notification for: " + userDto.getUsername()+ " has been stopped.";
    }
}
