package com.sensei.repository;

import com.sensei.entity.User;
import com.sensei.entity.Wallet;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Transactional
@Repository
public interface WalletDao extends CrudRepository<Wallet, Long> {

    @Override
    Wallet save(Wallet wallet);
    @Override
    Optional<Wallet> findById(Long id);
}
