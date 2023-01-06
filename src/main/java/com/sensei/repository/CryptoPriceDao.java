package com.sensei.repository;

import com.sensei.entity.CryptoPrice;
import jakarta.transaction.Transactional;
import org.springframework.cglib.core.Local;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CryptoPriceDao extends CrudRepository<CryptoPrice, Long> {
    @Override
    CryptoPrice save(CryptoPrice cryptoPrice);
    Optional<CryptoPrice> findBySymbol(String symbol);
    List<CryptoPrice> findBySymbolContainingIgnoreCaseOrderByTimeDesc(String symbol);
    List<CryptoPrice> findAllByTimeIsAfter(LocalDateTime time);
}
