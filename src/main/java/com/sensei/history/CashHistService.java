package com.sensei.history;

import com.sensei.history.CashHistory;
import com.sensei.history.CashHistoryDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
