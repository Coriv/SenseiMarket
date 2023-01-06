package com.sensei.service;

import com.sensei.entity.CryptoFlowHistory;
import com.sensei.repository.CryptoFlowHistoryDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoFlowHistService {

    private final CryptoFlowHistoryDao repository;

    public List<CryptoFlowHistory> getAllByUserId(Long userId) {
        return repository.findAllByUser(userId);
    }

    public CryptoFlowHistory save(CryptoFlowHistory cryptoFlowHistory) {
        return repository.save(cryptoFlowHistory);
    }
}
