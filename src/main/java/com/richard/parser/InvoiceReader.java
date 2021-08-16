package com.richard.parser;

import com.google.gson.Gson;
import com.richard.collecting.InvoiceOverview;
import com.richard.collecting.PaymentGroup;
import com.richard.collecting.Person;
import com.richard.collecting.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;

import static com.richard.parser.Currency.*;

public class InvoiceReader {

    private InvoiceOverview invoiceOverview;

    public InvoiceReader(InvoiceOverview invoiceOverview) {
        this.invoiceOverview = invoiceOverview;
    }

    public InvoiceReader(){
        invoiceOverview = new InvoiceOverview();
    }



    public void readAndConvertFile() throws IOException {
        String fileContent = readInvoiceFile();
        parseContent(fileContent);
    }

    private void parseContent(String fileContent){
        Gson gson = new Gson();
        TransactionListDO transactions = gson.fromJson(fileContent, TransactionListDO.class);
        transformToTransactions(transactions);
    }

    private void transformToTransactions(TransactionListDO transactionListDO){
        TransactionDO[] transactions = transactionListDO.getTransactions();

        // convert every json transaction object to a java object and add it to the monitored transactions
        for (TransactionDO t : transactions) {// convert String creditor from json to Person object
            Optional<Person> optCreditor = invoiceOverview.getInvolvedPersons().stream()
                    .filter(k -> k.getName().equals(t.getCreditor()))
                    .findFirst();
            Person creditor = optCreditor.orElseGet(() -> {
                Person person = new Person(t.getCreditor());
                invoiceOverview.getInvolvedPersons().add(person);
                return person;
            });

            // amount fits already
            double amount = t.getAmount();

            if(t.getCurrency() == KC.getId()){
                amount = CrownsToEuroConverter.crownsToEuros(amount);
            }

            // description fits already
            String description = t.getDescription();

            // convert List of Strings to PaymentGroup object
            ArrayList<String> paymentGroupStringList = t.getPaymentGroup();
            ArrayList<Person> personsForPaymentGroup = new ArrayList<>();
            for (String personName : paymentGroupStringList) {
                Optional<Person> optPerson = invoiceOverview.getInvolvedPersons().stream()
                        .filter(person -> person.getName().equals(personName))
                        .findFirst();
                Person newPersonForPaymentGroup;
                if(optPerson.isPresent()){
                    newPersonForPaymentGroup = optPerson.get();
                } else {
                    newPersonForPaymentGroup = new Person(personName);
                    invoiceOverview.getInvolvedPersons().add(newPersonForPaymentGroup);
                }
                personsForPaymentGroup.add(newPersonForPaymentGroup);
            }
            PaymentGroup paymentGroup = invoiceOverview.getPaymentGroupManager().getOrCreatePaymentGroup(personsForPaymentGroup);

            // create a new transaction
            Transaction transaction = new Transaction(
                    creditor,
                    amount,
                    paymentGroup,
                    description
            );

            // add it
            invoiceOverview.getTransactions().add(transaction);
        }
    }

    private String readInvoiceFile() throws IOException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("invoices.json");
        InputStreamReader isr = null;
        if (resourceAsStream != null) {
            isr = new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8);
        }

        BufferedReader br = null;
        if (isr != null) {
            br = new BufferedReader(isr);
        }
        String all = "";
        String line;
        if (br != null) {
            while((line = br.readLine()) != null){
                all += line;
            }
        }
        return all;
        //return "{" + all.replaceAll("\"", "\\\\\"") + "}";
    }
}