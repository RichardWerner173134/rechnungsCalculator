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
    private double amount;
    private IPaymentGroup paymentGroup;

}
