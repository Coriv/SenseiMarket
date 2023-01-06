package com.sensei.service;

import com.sensei.entity.CashFlowHistory;
import com.sensei.repository.CashFlowHistoryDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CashFlowHistService {

    private final CashFlowHistoryDao historyDao;

    public List<CashFlowHistory> getAllByUserId(Long userId) {
        return historyDao.findAllByUser(userId);
    }

    public CashFlowHistory save(CashFlowHistory cashFlowHistory) {
        return historyDao.save(cashFlowHistory);
    }
}
