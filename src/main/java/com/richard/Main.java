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

    public Main(){
        invoiceOverview = new InvoiceOverview();
        collect();

        calculator = new Calculator(invoiceOverview);
        calculate();
    }

    public static void main(String[] args) {

        new Main();
    }

    private void calculate() {
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
                sb.append(", value: ").append(value).append("€");
                sb.append("\n");
            }
        }

        System.out.println(sb.toString());
    }

    private void collect(){
        InvoiceReader invoiceReader = new InvoiceReader(invoiceOverview);
        try {
            invoiceReader.readAndConvertFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
