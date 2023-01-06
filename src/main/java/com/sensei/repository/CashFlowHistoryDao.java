package com.sensei.repository;

import com.sensei.entity.CashFlowHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface CashFlowHistoryDao extends CrudRepository<CashFlowHistory, Long> {

    @Override
    CashFlowHistory save(CashFlowHistory cashFlowHistory);

    @Override
    List<CashFlowHistory> findAll();
}
