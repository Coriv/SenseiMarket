package com.sensei.repository;

import com.sensei.entity.User;
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
    User save(User user);
    @Override
    void deleteById(Long id);
}
