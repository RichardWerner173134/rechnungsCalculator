package com.richard.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
class TransactionDO{
    private String creditor;
    private double amount;
    private int currency;
    private String description;
    private ArrayList<String> paymentGroup;
}

