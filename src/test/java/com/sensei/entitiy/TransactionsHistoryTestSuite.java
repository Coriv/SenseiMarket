package com.sensei.entitiy;

import com.sensei.entity.TransactionHistory;
import com.sensei.entity.TransactionType;
import com.sensei.entity.User;
import com.sensei.repository.TransactionHistoryDao;
import com.sensei.repository.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransactionsHistoryTestSuite {

    @Autowired
    private TransactionHistoryDao transactionHistoryDao;

    @Autowired
    private UserDao userDao;

    @Test
    public void addingTransactionToHistoryTest() {
        //Given
        User user = new User();
        user.setFirstName("Sebastian");
        user.setLastName("Boron");
        user.setDateOfJoin(LocalDateTime.now());
        user.setActive(true);
        user.setUsername("Coriver");
        user.setPassword("Password");
        user.setEmail("sebastian@kodilla.com");
        TransactionHistory transaction = new TransactionHistory();
        transaction.setTransactionType(TransactionType.BUY);
        transaction.setCryptoPair("BTCETH");
        transaction.setPrice(BigDecimal.valueOf(123));
        transaction.setQuantity(BigDecimal.valueOf(2134));
        transaction.setValue(transaction.getQuantity().multiply(transaction.getPrice()));
        transaction.setUser(user);
        transaction.setTransactionTime(LocalDateTime.now());

        //When
        userDao.save(user);
        transactionHistoryDao.save(transaction);
        Optional<TransactionHistory> resultTransaction = transactionHistoryDao.findById(transaction.getId());
        //Then
        assertTrue(resultTransaction.isPresent());
        assertEquals(resultTransaction.get().getPrice().doubleValue(), 123.00);
        assertEquals(resultTransaction.get().getCryptoPair(), "BTCETH");
        assertEquals(resultTransaction.get().getTransactionType(), TransactionType.BUY);
        //CleanUp
        userDao.deleteById(user.getId());
    }
}
