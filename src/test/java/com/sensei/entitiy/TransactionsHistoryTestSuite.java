package com.sensei.entitiy;

import com.sensei.entity.TradeHistory;
import com.sensei.entity.User;
import com.sensei.entity.TransactionType;
import com.sensei.exception.HistoricalTransactionNotFoundException;
import com.sensei.repository.TransactionHistoryDao;
import com.sensei.repository.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransactionsHistoryTestSuite {

    @Autowired
    private TransactionHistoryDao transactionHistoryDao;

    @Autowired
    private UserDao userDao;

    @Test
    public void addingTransactionToHistoryTest() throws HistoricalTransactionNotFoundException {
        //Given
        User user = new User();
        user.setFirstName("Sebastian");
        user.setLastName("Boron");
        user.setDateOfJoin(LocalDateTime.now());
        user.setActive(true);
        user.setUsername("Coriver");
        user.setPassword("Password");
        user.setEmail("sebastian@kodilla.com");
        TradeHistory transaction = new TradeHistory();
        transaction.setTransactionType(TransactionType.BUY);
        transaction.setCryptocurrency("BTCETH");
        transaction.setPrice(BigDecimal.valueOf(123));
        transaction.setQuantity(BigDecimal.valueOf(2134));
        transaction.setValue(transaction.getQuantity().multiply(transaction.getPrice()));
        transaction.setUser(user);
        transaction.setTransactionTime(LocalDateTime.now());

        //When
        userDao.save(user);
        transactionHistoryDao.save(transaction);
        TradeHistory resultTransaction = transactionHistoryDao.findById(transaction.getId()).orElseThrow(HistoricalTransactionNotFoundException::new);
        //Then
        assertEquals(resultTransaction.getPrice().doubleValue(), 123.00);
        assertEquals(resultTransaction.getCryptocurrency(), "BTCETH");
        assertEquals(resultTransaction.getTransactionType(), TransactionType.BUY);
        //CleanUp
        userDao.deleteById(user.getId());
        transactionHistoryDao.deleteById(transaction.getId());
    }
}
