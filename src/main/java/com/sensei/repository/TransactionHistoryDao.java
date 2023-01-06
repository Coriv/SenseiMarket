package com.sensei.repository;

import com.sensei.entity.TradeHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface TransactionHistoryDao extends CrudRepository<TradeHistory, Long> {

    @Override
    TradeHistory save(TradeHistory tradeHistory);

    List<TradeHistory> findAllByCryptocurrency(String symbol);

    List<TradeHistory> findAllByUserId(Long userId);
}
