package com.sensei.entitiy;

import com.sensei.repository.WalletCryptoDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WalletCryptoDaoTestSuite {

    @Autowired
    private WalletCryptoDao walletCryptoDao;

    @Test
    public void findByWalletIdAndSymbolsTest() {

    }
}
