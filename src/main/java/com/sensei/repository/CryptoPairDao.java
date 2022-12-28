package com.sensei.repository;

import com.sensei.entity.CryptoPrice;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Transactional
@Repository
public interface CryptoPairDao extends CrudRepository<CryptoPrice, Long> {
    @Override
    CryptoPrice save(CryptoPrice cryptoPrice);
    Optional<CryptoPrice> findBySymbol(String symbol);
    void deleteBySymbol(String symbol);
}
