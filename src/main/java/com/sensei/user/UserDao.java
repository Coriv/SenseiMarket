package com.sensei.user;

import com.sensei.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserDao extends CrudRepository<User, Long> {

    @Override
    Optional<User> findById(Long id);
    @Override
    List<User> findAll();
    @Override
    User save(User User);
    @Override
    void deleteById(Long id);
    Optional<User> findByUsername(String username);
}
