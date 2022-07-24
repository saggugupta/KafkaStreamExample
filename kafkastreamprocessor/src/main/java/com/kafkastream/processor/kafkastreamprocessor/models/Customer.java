package com.kafkastream.processor.kafkastreamprocessor.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
       private String customerId;
       private String name;
       private String phone;
       private String accountId;

       @Override
       public boolean equals(Object o) {
              if (this == o) return true;
              if (o == null || getClass() != o.getClass()) return false;
              Customer customer = (Customer) o;
              return Objects.equals(customerId, customer.customerId) && Objects.equals(name, customer.name) && Objects.equals(phone, customer.phone) && Objects.equals(accountId, customer.accountId);
       }

       @Override
       public int hashCode() {
              return Objects.hash(customerId, name, phone, accountId);
       }
}
