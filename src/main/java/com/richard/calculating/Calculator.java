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
        summarizeBills();
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
        for(BillCollection bc : billCollections){
            Person p1 = bc.getOwner();
            Map<Person, Double> bills = bc.getBills();
            Iterator<Map.Entry<Person, Double>> iterator = bills.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<Person, Double> next = iterator.next();
                Person p2 = next.getKey();
                Double p1_p2 = next.getValue();

                // get the corresponding billCollection
                BillCollection bcInner = null;
                for(BillCollection bcSearch : billCollections){
                    if(bcSearch.getOwner() == p2){
                        bcInner = bcSearch;
                        break;
                    }
                }

                if(bcInner == null){
                    return;
                }

                Map<Person, Double> correspondingBills = bcInner.getBills();
                Iterator<Map.Entry<Person, Double>> iterator1 = correspondingBills.entrySet().iterator();
                double p2_p1 = 0;
                while(iterator1.hasNext()){
                    Map.Entry<Person, Double> next1 = iterator1.next();
                    if(next1.getKey() == p1) {
                        p2_p1 = next1.getValue();
                        break;
                    }
                }
                adoptBills(p1, p2, p1_p2, p2_p1);
            }
        }
    }

    private void adoptBills(Person p1, Person p2, double p1_p2, double p2_p1){
        BillCollection billCollectionP1 = getBillCollectionForPerson(p1);
        BillCollection billCollectionP2 = getBillCollectionForPerson(p2);

        if(p1_p2 == p2_p1){
            // remove both bills
            billCollectionP1.removeBillForPerson(p2);
            billCollectionP2.removeBillForPerson(p1);
        }

        if(p1_p2 == 0) {
            // remove bill from p1
            billCollectionP1.removeBillForPerson(p2);
        }

        if(p2_p1 == 0){
            // remove bill from p2
            billCollectionP2.removeBillForPerson(p1);
        }

        if(p1_p2 < p2_p1){
            // remove bill from p1
            // reduce bill from p2 by p1_p2
            billCollectionP1.removeBillForPerson(p2);
            billCollectionP2.reduceBillForPerson(p1, p1_p2);
        }

        if(p2_p1 < p1_p2){
            // remove bill from p2
            // reduce bill from p1 by p2_p1
            billCollectionP2.removeBillForPerson(p1);
            billCollectionP1.reduceBillForPerson(p2, p2_p1);
        }
    }
}
