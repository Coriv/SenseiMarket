package com.sensei.mapper;

import com.sensei.dto.TradeDto;
import com.sensei.entity.CryptoPair;
import com.sensei.entity.Trade;
import com.sensei.entity.Wallet;
import com.sensei.exception.CryptoPairDoesNotFoundException;
import com.sensei.exception.TransactionDoesNotBellowToAnyWalletException;
import com.sensei.repository.CryptoPairDao;
import com.sensei.repository.TradeDao;
import com.sensei.repository.WalletDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeMapper {
    private final TradeDao tradeDao;
    private final CryptoPairDao cryptoPairDao;
    private final WalletDao walletDao;

    public Trade mapToTrade(TradeDto tradeDto) throws CryptoPairDoesNotFoundException, TransactionDoesNotBellowToAnyWalletException {
        Trade trade;
        if (tradeDto.getId() != null) {
            trade = tradeDao.findById(tradeDto.getId()).orElse(new Trade());
        } else {
            trade = new Trade();
        }
        CryptoPair cryptoPair = cryptoPairDao.findBySymbol(tradeDto.getCryptoPair()).orElseThrow(CryptoPairDoesNotFoundException::new);
        trade.setCryptoPair(cryptoPair);
        trade.setTransactionType(tradeDto.getTransactionType());
        trade.setQuantity(tradeDto.getQuantity());
        trade.setPrice(tradeDto.getPrice());
        trade.setValue(trade.getQuantity().multiply(trade.getPrice()));
        trade.setOpen(trade.isOpen());
        trade.setOpenDate(tradeDto.getOpenTime());
        trade.setCloseDate(tradeDto.getCloseTime());
        Wallet wallet = walletDao.findById(tradeDto.getWalletId()).orElseThrow(TransactionDoesNotBellowToAnyWalletException::new);
        trade.setWallet(wallet);

        return trade;
    }

    public TradeDto mapToTradeDto(Trade trade) {
        return TradeDto.builder()
                .id(trade.getId())
                .cryptoPair(trade.getCryptoPair().getSymbol())
                .transactionType(trade.getTransactionType())
                .quantity(trade.getQuantity())
                .price(trade.getPrice())
                .value(trade.getValue())
                .open(trade.isOpen())
                .walletId(trade.getWallet().getId())
                .openTime(trade.getOpenDate())
                .closeTime(trade.getCloseDate())
                .build();
    }
    public List<TradeDto> mapToTradesListDto(List<Trade> trades) {
        return trades.stream()
                .map(this::mapToTradeDto)
                .collect(Collectors.toList());
    }
}
