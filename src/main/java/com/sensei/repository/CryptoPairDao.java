package com.sensei.repository;

import com.sensei.entity.CryptoPair;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Transactional
@Repository
public interface CryptoPairDao extends CrudRepository<CryptoPair, String> {
    @Override
    CryptoPair save(CryptoPair cryptoPair);
    Optional<CryptoPair> findBySymbol(String symbol);
    void deleteBySymbol(String symbol);
}
