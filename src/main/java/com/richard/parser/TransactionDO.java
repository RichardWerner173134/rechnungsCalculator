package com.richard.parser;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
class TransactionDO{
    private String creditor;
    private double amount;
    private String description;
    private ArrayList<String> paymentGroup;
}

