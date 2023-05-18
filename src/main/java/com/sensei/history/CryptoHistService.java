package com.sensei.history;

import com.sensei.history.CryptoHistory;
import com.sensei.history.CryptoHistoryDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoHistService {

    private final CryptoHistoryDao repository;

    public List<CryptoHistory> getAllByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }

    public CryptoHistory save(CryptoHistory cryptoHistory) {
        return repository.save(cryptoHistory);
    }
}
