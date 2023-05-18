package com.sensei.history;

import com.sensei.history.CashHistoryDto;
import com.sensei.history.CashHistory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CashHistMapper {
    public CashHistoryDto mapToCashHistoryDto(CashHistory cashHistory) {
        CashHistoryDto cashHistoryDto = new CashHistoryDto(
                cashHistory.getId(),
                cashHistory.getUser().getId(),
                cashHistory.getType(),
                cashHistory.getQuantityUSD(),
                cashHistory.getQuantityPLN(),
                cashHistory.getTime());
        if(cashHistory.getToAccount() != null) {
            cashHistoryDto.setToAccount(cashHistory.getToAccount());
        }
        return cashHistoryDto;
    }

    public List<CashHistoryDto> mapToCashHistoryDtoList(List<CashHistory> cashHistoryList) {
        return cashHistoryList.stream()
                .map(this::mapToCashHistoryDto)
                .collect(Collectors.toList());
    }
}
