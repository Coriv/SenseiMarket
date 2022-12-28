package com.sensei.entitiy;

import com.sensei.entity.Wallet;
import com.sensei.repository.WalletDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class WalletCryptoTestSuite {

    @Autowired
    private WalletDao walletDao;

    @Test
    public void findWallet() {
        Optional<Wallet> optionalWallet = walletDao.findById(3L);
        Assertions.assertTrue(optionalWallet.isPresent());
    }
}
