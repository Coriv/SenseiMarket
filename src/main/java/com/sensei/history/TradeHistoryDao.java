package com.sensei.history;

import com.sensei.history.TradeHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface TradeHistoryDao extends CrudRepository<TradeHistory, Long> {

    @Override
    TradeHistory save(TradeHistory tradeHistory);

    List<TradeHistory> findAllByCryptocurrency(String symbol);

    List<TradeHistory> findAllByUserId(Long userId);
}
