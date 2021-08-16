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


        return billCollections;
    }

    private void calculateBill(Transaction transaction) {
        double amount = transaction.getAmount();
        Person creditor = transaction.getCreditor();
        String description = transaction.getDescription();
        PaymentGroup paymentGroup = (PaymentGroup) transaction.getPaymentGroup();

        for(Person p : paymentGroup.getPersonList()){
            if(p.equals(creditor)){
                // this person already payed all of it == creditor
                continue;
            }
            BillCollection bc = getBillCollectionForPerson(p);
            double dividedAmount = new BigDecimal(amount / paymentGroup.getPersonList().size())
                    .setScale(2, RoundingMode.HALF_UP).doubleValue();;
            bc.addPayment(creditor, dividedAmount);
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

}
