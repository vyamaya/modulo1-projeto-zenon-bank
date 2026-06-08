package br.com.zenom;

import java.util.Optional;

public interface TransactionRepository {
    void save(Transaction transaction);
    Optional<Transaction> findByOriginName(String originName);
}
