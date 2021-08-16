package com.richard.calculating;

import com.richard.collecting.Person;

import java.util.*;

public class BillSummarizer {

    private List<BillCollection> billCollections;
    private boolean isSummarized;

    public BillSummarizer(List<BillCollection> billCollections){
        this.billCollections = billCollections;
        isSummarized = false;
    }

    public List<BillCollection> summarizeBills(){

        boolean hasChanged = false;

        //while(!hasChanged){
            for (BillCollection billCollection : billCollections) {
                Person p1 = billCollection.getOwner();

                Iterator<Person> iterator = billCollection.getBills().keySet().iterator();
                while (iterator.hasNext()) {
                    Person p2 = iterator.next();
                    double p1_p2 = billCollection.getBills().get(p2);

                    BillCollection bcP2 = getBillCollectionForPerson(p2);
                    Iterator<Person> iterator1 = bcP2.getBills().keySet().iterator();
                    while (iterator1.hasNext()) {
                        Person next = iterator1.next();
                        if (next == p1) {
                            double p2_p1 = bcP2.getBills().get(p1);
                            adoptBills(p1, p2, p1_p2, p2_p1);
                            hasChanged = true;
                            break;
                        }

                    }
                    /*if (hasChanged) {
                        hasChanged = false;
                        break;
                    }*/
                }
            }
        //}
        return billCollections;
    }

    private void a(Person p1, Person p2){

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
