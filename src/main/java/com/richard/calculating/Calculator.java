package com.richard.calculating;

import com.richard.collecting.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Calculator implements ICalculator{

    private InvoiceOverview invoiceOverview;
    private List<BillCollection> billCollections;

    public Calculator(InvoiceOverview invoiceOverview){
        this.invoiceOverview = invoiceOverview;
        this.billCollections = new ArrayList<>();
    }

    public List<BillCollection> calculateAllBills() {
        this.billCollections = new ArrayList<>();

        for(Transaction transaction : invoiceOverview.getTransactions()){
            calculateBill(transaction);
        }
        //summarizeBills();
        return billCollections;
    }

    private void calculateBill(Transaction transaction) {
        double amount = transaction.getAmount();
        Person creditor = transaction.getCreditor();
        String description = transaction.getDescription();
        PaymentGroup paymentGroup = (PaymentGroup) transaction.getPaymentGroup();

        for(Person p : paymentGroup.getPersonList()){
            BillCollection bc = getBillCollectionForPerson(p);
            if(p.equals(creditor)){
                // this person already payed all of it == creditor
                bc.getDescriptions().add(description);
                continue;
            }
            double dividedAmount = new BigDecimal(amount / paymentGroup.getPersonList().size())
                    .setScale(2, RoundingMode.HALF_UP).doubleValue();;
            bc.addPayment(creditor, dividedAmount, description);
        }

    }

    private BillCollection getBillCollectionForPerson(Person person){
        Optional<BillCollection> billCollection = billCollections.stream()
                .filter(bc -> bc.getOwner().equals(person))
                .findFirst();

        return billCollection.orElseGet(() -> {
            BillCollection newBillCollection1 = new BillCollection(person);
            billCollections.add(newBillCollection1);
            return newBillCollection1;
        });

    }

    private void summarizeBills(){
        // look through every bill of every bc
        // p1
        // p2 = p1.bc.bill1.creditor
        // p1_p2 = p1.bc.bill1.amount

        // look through every bill of p2, where creditor is p1
        // p2_p1 = p2.bc.bill3.amount

        BillSummarizer billSummarizer = new BillSummarizer(billCollections);
        billCollections = billSummarizer.summarizeBills();
    }
}
