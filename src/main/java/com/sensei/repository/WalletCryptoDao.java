package com.sensei.repository;

import com.sensei.entity.WalletCrypto;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface WalletCryptoDao extends CrudRepository<WalletCrypto, Long> {
    List<WalletCrypto> findAllByWalletId(Long walletId);

    Optional<WalletCrypto> findByWalletIdAndAndCryptocurrency_Symbol(Long walletId, String symbol);
}
