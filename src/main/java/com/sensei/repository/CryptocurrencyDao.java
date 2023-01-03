package com.sensei.repository;

import com.sensei.entity.Cryptocurrency;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CryptocurrencyDao extends CrudRepository<Cryptocurrency, String> {

    Optional<Cryptocurrency> findBySymbol(String symbol);

    @Override
    List<Cryptocurrency> findAll();

    @Override
    Cryptocurrency save(Cryptocurrency cryptocurrency);

    void deleteBySymbol(String symbol);
}
