package com.sensei.cryptoPrice;

import com.sensei.cryptoPrice.CryptoPrice;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
