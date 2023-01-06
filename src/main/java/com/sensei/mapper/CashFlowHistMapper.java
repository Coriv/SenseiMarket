package com.sensei.mapper;

import com.sensei.dto.CashFlowHistoryDto;
import com.sensei.entity.CashFlowHistory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CashFlowHistMapper {
    public CashFlowHistoryDto mapToCashHistoryDto(CashFlowHistory cashHistory) {
        CashFlowHistoryDto cashHistoryDto = new CashFlowHistoryDto(
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

    public List<CashFlowHistoryDto> mapToCashHistoryDtoList(List<CashFlowHistory> cashHistoryList) {
        return cashHistoryList.stream()
                .map(this::mapToCashHistoryDto)
                .collect(Collectors.toList());
    }
}
