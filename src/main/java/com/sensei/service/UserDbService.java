package com.sensei.service;

import com.sensei.entity.User;
import com.sensei.entity.WalletCrypto;
import com.sensei.exception.NotEmptyWalletException;
import com.sensei.exception.UserNotFoundException;
import com.sensei.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDbService {

    private final UserDao userDao;

    public User findUserById(Long id) throws UserNotFoundException{
        return userDao.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public List<User> getAllUsers() {
       return userDao.findAll();
    }

    public User save(User user) {
        return userDao.save(user);
    }

    public User blockUser(User user) {
        user.setActive(!user.isActive());
        return userDao.save(user);
    }

    public void deleteUser(Long id) throws UserNotFoundException, NotEmptyWalletException {
        User user = userDao.findById(id).orElseThrow(UserNotFoundException::new);
        List<WalletCrypto> filteredList = user.getWallet().getCryptosList().stream()
                        .filter(n  -> n.getQuantity().doubleValue() != 0.00)
                                .collect(Collectors.toList());
        if(!filteredList.isEmpty()) {
            throw new NotEmptyWalletException();
        }
        userDao.deleteById(id);
    }
}
