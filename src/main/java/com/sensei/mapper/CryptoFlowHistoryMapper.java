package com.sensei.mapper;

import com.sensei.dto.CryptoFlowHistoryDto;
import com.sensei.entity.CryptoFlowHistory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CryptoFlowHistoryMapper {

    public CryptoFlowHistoryDto mapToCryptoFlowHistoryDto(CryptoFlowHistory cryptoFlowHistory) {
        return new CryptoFlowHistoryDto(
                cryptoFlowHistory.getId(),
                cryptoFlowHistory.getUser().getId(),
                cryptoFlowHistory.getSymbol(),
                cryptoFlowHistory.getType(),
                cryptoFlowHistory.getQuantity(),
                cryptoFlowHistory.getAddressTo(),
                cryptoFlowHistory.getTime()
        );
    }

    public List<CryptoFlowHistoryDto> mapToCryptoFlowHistList(List<CryptoFlowHistory> history) {
        return history.stream()
                .map(this::mapToCryptoFlowHistoryDto)
                .collect(Collectors.toList());
    }
}
