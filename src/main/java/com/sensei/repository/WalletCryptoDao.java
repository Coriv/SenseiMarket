package com.sensei.repository;

import com.sensei.entity.WalletCrypto;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface WalletCryptoDao extends CrudRepository<WalletCrypto, Long> {

    //Iterable<WalletCrypto> findAllById(List<Long> listOfId);
}
