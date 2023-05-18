package com.sensei.wallet;

import com.sensei.wallet.Wallet;
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

    @Override
    void deleteById(Long id);
}
