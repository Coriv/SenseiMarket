package com.sensei.walletCash;

import com.sensei.walletCash.CashWallet;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Transactional
@Repository
public interface CashWalletDao extends CrudRepository<CashWallet, Long> {
    CashWallet save(CashWallet cashWallet);
    Optional<CashWallet> findById(Long id);
}
