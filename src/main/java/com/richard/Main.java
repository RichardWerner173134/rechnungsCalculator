package com.richard;

import com.richard.calculating.Calculator;
import com.richard.collecting.InvoiceOverview;
import com.richard.collecting.PaymentGroup;
import com.richard.collecting.Person;

public class Main {
    public static void main(String[] args) {
        Person richard = new Person("Richard");
        Person thomas = new Person("Thomas");
        Person paul = new Person("Paul");
        Person lennart = new Person("Lennart");
        Person fabian = new Person("Fabian");


        InvoiceOverview invoiceOverview = new InvoiceOverview();
        Calculator calculator = new Calculator();

    }
}
