package com.sensei.service;

import com.sensei.entity.CashHistory;
import com.sensei.repository.CashHistoryDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class CashHistService {

    private final CashHistoryDao historyDao;

    public List<CashHistory> getAllByUserId(Long userId) {

        return historyDao.findAllByUserId(userId);
    }

    public CashHistory save(CashHistory cashHistory) {
        return historyDao.save(cashHistory);
    }
}
