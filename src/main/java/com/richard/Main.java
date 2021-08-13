package com.richard;

import com.richard.calculating.Calculator;
import com.richard.collecting.InvoiceOverview;
import com.richard.collecting.PaymentGroup;
import com.richard.collecting.Person;
import com.richard.collecting.Transaction;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Person richard = new Person("Richard");
        Person thomas = new Person("Thomas");
        Person paul = new Person("Paul");
        Person lennart = new Person("Lennart");
        Person fabian = new Person("Fabian");

        ArrayList<Person> allPersons = new ArrayList<>();
        allPersons.add(richard);
        allPersons.add(thomas);
        allPersons.add(paul);
        allPersons.add(lennart);
        allPersons.add(fabian);

        InvoiceOverview invoiceOverview = new InvoiceOverview(Arrays.asList(
                richard,
                lennart,
                thomas,
                fabian,
                paul
        ));
        Calculator calculator = new Calculator();

        Transaction t1 = new Transaction();
        t1.setAmount(20.00);
        t1.setCreditor(richard);
        t1.setDescription("bierkiste");
        t1.setPaymentGroup(invoiceOverview.getPaymentGroupManager().getOrCreatePaymentGroup(Arrays.asList(richard, paul, thomas, lennart, fabian)));

        invoiceOverview.addTransaction(t1);
    }
}
