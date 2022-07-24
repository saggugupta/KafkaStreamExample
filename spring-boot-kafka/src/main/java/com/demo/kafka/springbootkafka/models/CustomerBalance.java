package com.demo.kafka.springbootkafka.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerBalance {
    private String accountId;
    private String customerId;
    private String phone;
    private String balance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerBalance that = (CustomerBalance) o;
        return Objects.equals(accountId, that.accountId) && Objects.equals(customerId, that.customerId) && Objects.equals(phone, that.phone) && Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, customerId, phone, balance);
    }
}
