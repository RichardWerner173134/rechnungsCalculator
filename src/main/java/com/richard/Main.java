package com.richard;

import com.richard.calculating.BillCollection;
import com.richard.calculating.Calculator;
import com.richard.calculating.BillPair;
import com.richard.collecting.*;
import com.richard.parser.InvoiceReader;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
        StringBuilder sb = new StringBuilder();
        sb.append(" ______     _____   ________   _______              _________   _______      _____   _______                   _____     __    \n" +
                "|_   _ \\   |_   _| |_   __  | |_   __ \\            |  _   _  | |_   __ \\    |_   _| |_   __ \\                 / ___ `.  /  |   \n" +
                "  | |_) |    | |     | |_ \\_|   | |__) |           |_/ | | \\_|   | |__) |     | |     | |__) |               |_/___) |  `| |   \n" +
                "  |  __'.    | |     |  _| _    |  __ /                | |       |  __ /      | |     |  ___/                 .'____.'   | |   \n" +
                " _| |__) |  _| |_   _| |__/ |  _| |  \\ \\_             _| |_     _| |  \\ \\_   _| |_   _| |_                   / /_____   _| |_  \n" +
                "|_______/  |_____| |________| |____| |___|           |_____|   |____| |___| |_____| |_____|                  |_______| |_____| \n" +
                "                                                                                                                               ");

        System.out.println(sb.toString());
        new Main();
    }

    private void calculate() {
        List<BillCollection> billCollections = calculator.calculateAllBills();
        printBills(billCollections);
    }

    private void printBills(List<BillCollection> billCollections) {
        StringBuilder sb = new StringBuilder();
        sb.append("\nBills: ");
        BillPair.getSummarizedStuff(billCollections);

        for(BillCollection bc : billCollections){
            sb.append("\n\t==========================================");
            sb.append("\n\tPerson: ").append(bc.getOwner().getName()).append("\n");
            sb.append("\t\tPaying for:\n");
            bc.getDescriptions().forEach(desc -> sb.append("\t\t\t- ").append(desc).append("\n"));
            Iterator<Map.Entry<Person, Double>> entries = bc.getBills().entrySet().iterator();

            while(entries.hasNext()){
                Map.Entry<Person, Double> next = entries.next();
                Person key = next.getKey();
                BillPair billPair = BillPair.getBillPairForPersons(bc.getOwner(), key);
                double amountForPerson = billPair.getAmountForPerson(bc.getOwner());

                sb.append("\n\t\tTo: ").append(key.getName());
                sb.append(", value: ").append(amountForPerson).append("€");
                sb.append("\n\t------------------------------------------");
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
        printInvoices();
    }

    private void printInvoices(){
        StringBuilder sb = new StringBuilder();

        sb.append("Involved Persons: ");
        invoiceOverview.getInvolvedPersons().forEach(p -> sb.append("\n\t- ").append(p.getName()));

        sb.append("\nTransactions: ");

        int i = 1;
        for (Transaction transaction : invoiceOverview.getTransactions()) {
            sb.append("\n\t[" + i + "] ");
            sb.append("\n\tCreditor: " + transaction.getCreditor().getName());
            sb.append("\n\tAmount: " + new BigDecimal(transaction.getAmount()).setScale(2, RoundingMode.HALF_UP).toString() + "€");
            sb.append("\n\tDescription: " + transaction.getDescription());
            sb.append("\n\tPaymentGroup: ");
            ((PaymentGroup) transaction.getPaymentGroup()).getPersonList().forEach(person -> {
                sb.append("\n\t\t- ").append(person.getName());
            });
            sb.append("\n\t------------------------------------------");
            i++;
        }
        System.out.println(sb.toString());
    }
}
