package com.sensei.repository;

import com.sensei.entity.TransactionHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface TransactionHistoryDao extends CrudRepository<TransactionHistory, Long> {

    @Override
    TransactionHistory save(TransactionHistory transactionHistory);

    List<TransactionHistory> findAllByCryptocurrency(String symbol);

    List<TransactionHistory> findAllByUserId(Long userId);
}
