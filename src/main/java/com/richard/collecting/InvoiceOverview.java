package com.richard.collecting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceOverview {
    private List<Person> involvedPersons;
    private PaymentGroupManager paymentGroupManager;

    private List<Transaction> transactions;

    public InvoiceOverview(List<Person> involvedPersons){
        this.involvedPersons = involvedPersons;
        paymentGroupManager = new PaymentGroupManager();
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }



}
