package br.com.zenom;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Main {

    void main() {
        var t1 = new Transaction(1, TransactionType.PAYMENT, new BigDecimal("9838.64"),
                        new TransactionCustomer("C1231006815", new BigDecimal("170136.0"), new BigDecimal("160296.36")),
                        new TransactionCustomer("M1979787155", new BigDecimal("0.0"), new BigDecimal("0.0")),
                        false,false
        );
        var t2 = new Transaction(743, TransactionType.CASH_OUT, new BigDecimal("850002.52"),
                new TransactionCustomer("C1280323807", new BigDecimal("850002.52"), new BigDecimal("0.0")),
                new TransactionCustomer("C873221189", new BigDecimal("6510099.11"), new BigDecimal("7360101.63")),
                true,false
        );

        IO.println(t1);
        IO.println(t2);

        IO.println("------------------------------------------------------------------------------");

        var transactionIngestor = new TransactionIngestor();
        List<Transaction> transactions = transactionIngestor.read("data/PS_20174392719_1491204439457_log.csv");
        IO.println(transactions.size());

        transactions.stream().limit(10).forEach(IO::println);

        IO.println("------------------------------------------------------------------------------");

        List<Transaction> transactionsBadData = transactionIngestor.read("data/paysim_with_bad_data.csv");
        IO.println(transactionsBadData.size());

        transactionsBadData.forEach(IO::println);

        IO.println("------------------------------------------------------------------------------");

        var fraudAnalyzer = new FraudAnalyzer(transactions);

        long fraudCount = fraudAnalyzer.countFrauds();
        IO.println("1. Total de Fraudes: " + fraudCount);

        List<BigDecimal> highestFraudAmount = fraudAnalyzer.findHighestValueFraudAmounts(3 );

        IO.println("2. Top 3 Fraudes de Maior Valor: ");
        highestFraudAmount.forEach(amount -> IO.println("- %.2f".formatted(amount)));

        List<String> suspiciousClients = fraudAnalyzer.findTopSuspiciousClients(5);
        IO.println("3. Clientes Suspeitos: " + suspiciousClients);

        BigDecimal totalFraudLoss = fraudAnalyzer.calculateTotalFraudsLoss();
        IO.println("4. Prejuízo Total" + totalFraudLoss);

        Map<TransactionType, Long> fraudCountByType = fraudAnalyzer.countFraudsByType();
        IO.println("5. Fraudes por Tipo: ");
        fraudCountByType.forEach((TransactionType type, Long count) -> IO.println("- %s: %d".formatted(type,count)));

        IO.println("------------------------------------------------------------------------------");

        TransactionRepository transactionRepository;

        transactionRepository = new TransactionListRepository(transactions);
        var notFoundOriginName = "C12345";
        var existsOriginName = "C1868032458";

        transactionRepository.findByOriginName(notFoundOriginName)
                .ifPresentOrElse(IO::println, () -> IO.println("Transação não encontrada para o cliente " + notFoundOriginName));

        long starTimeList = System.nanoTime();
        transactionRepository.findByOriginName(existsOriginName)
                .ifPresentOrElse(IO::println, () -> IO.println("Transação não encontrada para o cliente " + notFoundOriginName));
        long endTimeList = System.nanoTime();
        IO.println("Tempo de busca - List (ms): " + (endTimeList - starTimeList) / 1_000_000.0);

        transactionRepository = new TransactionMapRepository(transactions);
        transactionRepository.findByOriginName(notFoundOriginName)
                .ifPresentOrElse(IO::println, () -> IO.println("Transação não encontrada para o cliente " + notFoundOriginName));

        long starTimeMap = System.nanoTime();
        transactionRepository.findByOriginName(existsOriginName)
                .ifPresentOrElse(IO::println, () -> IO.println("Transação não encontrada para o cliente " + notFoundOriginName));
        long endTimeMap = System.nanoTime();
        IO.println("Tempo de busca - Map (ms): " + (endTimeMap - starTimeMap) / 1_000_000.0);

    }
}
