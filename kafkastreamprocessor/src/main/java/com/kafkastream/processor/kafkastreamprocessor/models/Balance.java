package com.kafkastream.processor.kafkastreamprocessor.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Balance {
    private String balanceId;
    private String accountId;
    private String balance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Balance balance1 = (Balance) o;
        return Objects.equals( balance, balance1.balance) && Objects.equals(balanceId, balance1.balanceId) && Objects.equals(accountId, balance1.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balanceId, accountId, balance);
    }
}
