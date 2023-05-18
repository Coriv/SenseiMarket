package com.sensei.history;

import com.sensei.history.CryptoHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface CryptoHistoryDao extends CrudRepository<CryptoHistory, Long> {

    @Override
    CryptoHistory save(CryptoHistory cryptoHistory);

    List<CryptoHistory> findAllByUserId(Long userId);
}
