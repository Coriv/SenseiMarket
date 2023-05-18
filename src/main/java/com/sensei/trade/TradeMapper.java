package com.sensei.trade;

import com.sensei.cryptocurrency.Cryptocurrency;
import com.sensei.wallet.Wallet;
import com.sensei.exception.CryptocurrencyNotFoundException;
import com.sensei.exception.WalletNotFoundException;
import com.sensei.cryptocurrency.CryptocurrencyDao;
import com.sensei.wallet.WalletDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeMapper {
    private final TradeDao tradeDao;
    private final CryptocurrencyDao cryptocurrencyDao;
    private final WalletDao walletDao;

    public Trade mapToTrade(TradeDto tradeDto) throws WalletNotFoundException, CryptocurrencyNotFoundException {
        Trade trade;
        if (tradeDto.getId() != null) {
            trade = tradeDao.findById(tradeDto.getId()).orElse(new Trade());
        } else {
            trade = new Trade();
        }
        Cryptocurrency cryptocurrency = cryptocurrencyDao.findBySymbol(tradeDto.getCryptoSymbol()).orElseThrow(CryptocurrencyNotFoundException::new);
        trade.setCryptocurrency(cryptocurrency);
        trade.setTransactionType(tradeDto.getTransactionType());
        trade.setQuantity(tradeDto.getQuantity());
        trade.setPrice(tradeDto.getPrice());
        trade.setValue(trade.getQuantity().multiply(trade.getPrice()));
        trade.setOpen(trade.isOpen());
        trade.setOpenDate(tradeDto.getOpenTime());
        trade.setCloseDate(tradeDto.getCloseTime());
        Wallet wallet = walletDao.findById(tradeDto.getWalletId()).orElseThrow(WalletNotFoundException::new);
        trade.setWallet(wallet);

        return trade;
    }

    public TradeDto mapToTradeDto(Trade trade) {
        return TradeDto.builder()
                .id(trade.getId())
                .cryptoSymbol(trade.getCryptocurrency().getSymbol())
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
