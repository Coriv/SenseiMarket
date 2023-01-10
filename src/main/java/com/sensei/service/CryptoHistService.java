package com.sensei.service;

import com.sensei.entity.CryptoHistory;
import com.sensei.repository.CryptoHistoryDao;
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
