package com.sensei.service;

import com.sensei.entity.Cryptocurrency;
import com.sensei.entity.WalletCrypto;
import com.sensei.exception.WalletNotFoundException;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WalletDbServiceTestSuite {

    @Autowired
    private WalletDbService walletDbService;

    @Test
    void createWallet() {
    }

    @Test
    void getListOfCrypto() throws WalletNotFoundException {

    }

    @Test
    void deleteWallet() {
    }
}