package com.sensei.trade;

import com.sensei.trade.Trade;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface TradeDao extends CrudRepository<Trade, Long> {
    @Override
    Trade save(Trade trade);
    @Override
    Optional<Trade> findById(Long id);
    List<Trade> findAllByWalletId(Long walletId);
    List<Trade> findAllByOpenIsTrue();
}
