package com.richard.collecting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.AnnotatedArrayType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
public class InvoiceOverview {
    private List<Person> involvedPersons;
    private PaymentGroupManager paymentGroupManager;

    private List<Transaction> transactions;

    public InvoiceOverview(){
        involvedPersons = new ArrayList<>();
        paymentGroupManager = new PaymentGroupManager();
        transactions = new ArrayList<>();
    }

    public InvoiceOverview(List<Person> involvedPersons){
        this.involvedPersons = involvedPersons;
        paymentGroupManager = new PaymentGroupManager();
        transactions = new ArrayList<>();
    }
}
