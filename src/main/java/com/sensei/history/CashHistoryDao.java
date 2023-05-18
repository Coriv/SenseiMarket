package com.sensei.history;

import com.sensei.history.CashHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface CashHistoryDao extends CrudRepository<CashHistory, Long> {

    @Override
    CashHistory save(CashHistory cashHistory);

    List<CashHistory> findAllByUserId(Long userId);
}
