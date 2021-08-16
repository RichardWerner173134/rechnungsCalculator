package com.richard.collecting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private Person creditor;
    // 0 = Euro, 1 = Kc
    private double amount;
    private IPaymentGroup paymentGroup;
    private String description;
}
