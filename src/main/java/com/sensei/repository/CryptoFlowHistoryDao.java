package com.sensei.repository;

import com.sensei.entity.CryptoFlowHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface CryptoFlowHistoryDao extends CrudRepository<CryptoFlowHistory, Long> {

    @Override
    CryptoFlowHistory save(CryptoFlowHistory cryptoFlowHistory);

    List<CryptoFlowHistory> findAllByUser(Long userId);
}
