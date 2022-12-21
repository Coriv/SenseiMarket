package com.sensei.service;

import com.sensei.entity.User;
import com.sensei.exception.UserNotFoundException;
import com.sensei.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void deleteUser(Long id) {
        userDao.deleteById(id);
    }
}
