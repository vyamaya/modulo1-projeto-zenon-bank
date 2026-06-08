package br.com.zenom;

import java.util.List;

public class DBMain {

    void main() {

        ConnectionFactory.getConnection();
        IO.println("Conexão com o BD criada!");

        var repository = new TransactionSQLRepository();
        var transactionIngestor = new TransactionIngestor();

        long starTimeList = System.nanoTime();
        List<Transaction> transactions = transactionIngestor.read("data/PS_20174392719_1491204439457_log.csv");
        IO.println(transactions.size());

        IO.println("Iniciando adicao das transacoes no BD...");
        transactions.forEach(repository::save);

        long endTimeList = System.nanoTime();

        IO.println("Tempo de insercao - SQL (ms): " + (endTimeList - starTimeList) / 1_000_000.0);

        repository.findByOriginName("C1231006815")
                .ifPresentOrElse(IO::println, () -> IO.println("Transacão não encontrada para: C1231006815"));
        repository.findByOriginName("C12345")
                .ifPresentOrElse(IO::println, () -> IO.println("Transacão não encontrada para: C12345"));


    }

}
