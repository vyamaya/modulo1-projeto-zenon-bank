package br.com.zenom;

import java.math.BigDecimal;

public record TransactionCustomer (String name, BigDecimal oldBalance, BigDecimal newBalance) {
}
