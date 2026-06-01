package br.com.zenom;

import java.util.Optional;

public interface TransactionRepository {
    Optional<Transaction> findByOriginName(String originName);
}
