package com.sensei.mapper;

import com.sensei.trade.TradeDto;
import com.sensei.cryptocurrency.Cryptocurrency;
import com.sensei.trade.Trade;
import com.sensei.trade.TradeMapper;
import com.sensei.trade.TransactionType;
import com.sensei.wallet.Wallet;
import com.sensei.exception.CryptocurrencyNotFoundException;
import com.sensei.exception.WalletNotFoundException;
import com.sensei.cryptocurrency.CryptocurrencyDao;
import com.sensei.wallet.WalletDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class TradeMapperTestSuite {

    @Autowired
    private TradeMapper tradeMapper;
    @MockBean
    private WalletDao walletDao;
    @MockBean
    private CryptocurrencyDao cryptocurrencyDao;

    @Test
    void mapToTradeTest() throws WalletNotFoundException, CryptocurrencyNotFoundException {
        //Given
        TradeDto tradeDto = TradeDto.builder()
                .cryptoSymbol("MATIC")
                .transactionType(TransactionType.BUY)
                .quantity(BigDecimal.valueOf(100))
                .price(BigDecimal.valueOf(2.00))
                .value(BigDecimal.valueOf(200))
                .open(true)
                .walletId(10L)
                .openTime(LocalDateTime.now())
                .closeTime(LocalDateTime.of(2023, 1, 1, 10, 00))
                .build();
        Wallet wallet = new Wallet();
        Cryptocurrency btc = new Cryptocurrency("MATIC", "Bitcoin");
        when(walletDao.findById(any())).thenReturn(Optional.of(wallet));
        when(cryptocurrencyDao.findBySymbol(any())).thenReturn(Optional.of(btc));
        //When
        Trade trade = tradeMapper.mapToTrade(tradeDto);
        //Then
        assertEquals(trade.getCryptocurrency().getSymbol(), "MATIC");
        assertEquals(trade.getTransactionType(), TransactionType.BUY);
        assertEquals(trade.getQuantity(), BigDecimal.valueOf(100));
        assertEquals(trade.getPrice(), BigDecimal.valueOf(2.00));
        assertEquals(trade.getValue(), BigDecimal.valueOf(200.0));
        assertTrue(trade.isOpen());
        assertEquals(trade.getOpenDate(), tradeDto.getOpenTime());
        assertEquals(trade.getCloseDate(), tradeDto.getCloseTime());
    }

    @Test
    void mapToTradeDtoTest() {
        Cryptocurrency btc = new Cryptocurrency("BTC", "Bitcoin");
        Wallet wallet = new Wallet();
        Trade trade = new Trade();
        trade.setCryptocurrency(btc);
        trade.setTransactionType(TransactionType.BUY);
        trade.setQuantity(BigDecimal.valueOf(100));
        trade.setPrice(BigDecimal.valueOf(2.00));
        trade.setValue(BigDecimal.valueOf(200.0));
        trade.setOpen(true);
        trade.setWallet(wallet);
        trade.setOpenDate(LocalDateTime.now());
        trade.setCloseDate(LocalDateTime.of(2023, 1, 1, 10, 00));
        //when
        TradeDto tradeDto = tradeMapper.mapToTradeDto(trade);
        //Then
        assertEquals(tradeDto.getId(), trade.getId());
        assertEquals(tradeDto.getCryptoSymbol(), "BTC");
        assertEquals(tradeDto.getTransactionType(), TransactionType.BUY);
        assertEquals(tradeDto.getQuantity(), BigDecimal.valueOf(100));
        assertEquals(tradeDto.getPrice(), BigDecimal.valueOf(2.00));
        assertEquals(tradeDto.getValue(), BigDecimal.valueOf(200.0));
        assertTrue(tradeDto.isOpen());
        assertEquals(tradeDto.getOpenTime(), tradeDto.getOpenTime());
        assertEquals(tradeDto.getCloseTime(), tradeDto.getCloseTime());
    }

    @Test
    void mapToTradesListDtoTest() {
        //Given
        Cryptocurrency btc = new Cryptocurrency("BTC", "Bitcoin");
        Wallet wallet = new Wallet();
        Trade trade = new Trade();
        trade.setCryptocurrency(btc);
        trade.setTransactionType(TransactionType.BUY);
        trade.setQuantity(BigDecimal.valueOf(100));
        trade.setPrice(BigDecimal.valueOf(2.00));
        trade.setValue(BigDecimal.valueOf(200));
        trade.setOpen(true);
        trade.setWallet(wallet);
        trade.setOpenDate(LocalDateTime.now());
        trade.setCloseDate(LocalDateTime.of(2023, 1, 1, 10, 00));
        List<Trade> trades = Arrays.asList(trade);
        //When
        List<TradeDto> tradesDto = tradeMapper.mapToTradesListDto(trades);
        //then
        assertEquals(tradesDto.size(), 1);
        assertEquals(tradesDto.get(0).getId(), trade.getId());
        assertEquals(tradesDto.get(0).getCryptoSymbol(), "BTC");
        assertEquals(tradesDto.get(0).getTransactionType(), TransactionType.BUY);
        assertEquals(tradesDto.get(0).getQuantity(), BigDecimal.valueOf(100));
        assertEquals(tradesDto.get(0).getPrice(), BigDecimal.valueOf(2.00));
        assertEquals(tradesDto.get(0).getValue(), BigDecimal.valueOf(200));
        assertTrue(tradesDto.get(0).isOpen());
        assertEquals(tradesDto.get(0).getOpenTime(), trade.getOpenDate());
        assertEquals(tradesDto.get(0).getCloseTime(), trade.getCloseDate());
    }
}