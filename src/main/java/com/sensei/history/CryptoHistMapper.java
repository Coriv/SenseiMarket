package com.sensei.history;

import com.sensei.history.CryptoHistoryDto;
import com.sensei.history.CryptoHistory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CryptoHistMapper {

    public CryptoHistoryDto mapToCryptoFlowHistoryDto(CryptoHistory cryptoHistory) {
        return new CryptoHistoryDto(
                cryptoHistory.getId(),
                cryptoHistory.getUser().getId(),
                cryptoHistory.getSymbol(),
                cryptoHistory.getType(),
                cryptoHistory.getQuantity(),
                cryptoHistory.getAddressTo(),
                cryptoHistory.getTime()
        );
    }

    public List<CryptoHistoryDto> mapToCryptoFlowHistList(List<CryptoHistory> history) {
        return history.stream()
                .map(this::mapToCryptoFlowHistoryDto)
                .collect(Collectors.toList());
    }
}
