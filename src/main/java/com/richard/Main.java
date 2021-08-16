package com.richard;

import com.richard.calculating.BillCollection;
import com.richard.calculating.Calculator;
import com.richard.collecting.*;
import com.richard.parser.InvoiceReader;

import java.io.IOException;
import java.util.*;

public class Main {

    private Calculator calculator;
    private InvoiceOverview invoiceOverview;

    // create Persons
    /*private Person richard = new Person("Richard");
    private Person thomas = new Person("Thomas");
    private Person paul = new Person("Paul");
    private Person lennart = new Person("Lennart");
    private Person fabian = new Person("Fabian");
*/
    public Main(){

        // add all to list
        ArrayList<Person> allPersons = new ArrayList<>();
        /*allPersons.add(richard);
        allPersons.add(thomas);
        allPersons.add(paul);
        allPersons.add(lennart);
        allPersons.add(fabian);

        invoiceOverview = new InvoiceOverview(Arrays.asList(
                richard,
                lennart,
                thomas,
                fabian,
                paul
        ));*/
        invoiceOverview = new InvoiceOverview();

        collecting();

        calculator = new Calculator(invoiceOverview);
        calculating();
    }

    public static void main(String[] args) {
        new Main();
    }

    private void calculating() {
        List<BillCollection> billCollections = calculator.calculateAllBills();

        StringBuilder sb = new StringBuilder();
        for(BillCollection bc : billCollections){
            sb.append("Person: ").append(bc.getOwner().getName()).append("\n");


            Iterator<Map.Entry<Person, Double>> entries = bc.getBills().entrySet().iterator();

            while(entries.hasNext()){
                Map.Entry<Person, Double> next = entries.next();
                Person key = next.getKey();
                Double value = next.getValue();
                sb.append("\tTo: ").append(key.getName());
                sb.append(", value: ").append(value);
                sb.append("\n");
            }
        }

        System.out.println(sb.toString());
    }

    private void collecting(){

        InvoiceReader invoiceReader;
        invoiceReader = new InvoiceReader(invoiceOverview);
        try {
            invoiceReader.readAndConvertFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Transaction t1 = new Transaction();
        t1.setAmount(20.00);
        t1.setCreditor(richard);
        t1.setDescription("bierkiste");
        t1.setPaymentGroup(invoiceOverview.getPaymentGroupManager().getOrCreatePaymentGroup(Arrays.asList(richard, paul, thomas, lennart, fabian)));

        Transaction t2 = new Transaction();
        t2.setAmount(10.00);
        t2.setCreditor(thomas);
        t2.setDescription("Irgendwas andres");
        t2.setPaymentGroup(invoiceOverview.getPaymentGroupManager().getOrCreatePaymentGroup(Arrays.asList(paul, richard, thomas, lennart, fabian)));

        invoiceOverview.addTransaction(t1);
        invoiceOverview.addTransaction(t2);*/
    }
}
