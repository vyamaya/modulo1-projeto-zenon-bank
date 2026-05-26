package br.com.zenom;

import java.math.BigDecimal;
import java.util.Objects;

public record TransactionCustomer (String name, BigDecimal oldBalance, BigDecimal newBalance) {

    public TransactionCustomer {

        Objects.requireNonNull(name);
        Objects.requireNonNull(oldBalance);
        Objects.requireNonNull(newBalance);

        if (name.trim().isEmpty()) throw new IllegalArgumentException("name should not be empty");
        if (oldBalance.signum() == -1) throw new IllegalArgumentException("oldBalance should be positive: " + oldBalance);
        if (newBalance.signum() == -1) throw new IllegalArgumentException("newBalance should be positive: " + newBalance);

    }

}
