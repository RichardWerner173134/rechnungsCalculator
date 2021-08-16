package com.richard.calculating;

import com.richard.collecting.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BillPair {

    private static List<BillPair> billPairs;

    private Person p1;
    private Person p2;

    private double amountP1ToP2;
    private double amountP2ToP1;

    public BillPair(Person p1, Person to) {
        this.p1 = p1;
        this.p2 = to;
    }

    private void calcDiff(){
        if(amountP1ToP2 == amountP2ToP1){
            amountP1ToP2 = 0.0;
            amountP2ToP1 = 0.0;
        }
        if(amountP1ToP2 < amountP2ToP1){
            amountP2ToP1 -= amountP1ToP2;
            amountP1ToP2 = 0.0;
        }
        if(amountP1ToP2 > amountP2ToP1){
            amountP1ToP2 -= amountP2ToP1;
            amountP2ToP1 = 0.0;
        }
    }

    public double getAmountForPerson(Person p){
        calcDiff();
        if(p == p1){
            return amountP1ToP2;
        } else {
            return amountP2ToP1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        BillPair o = (BillPair)obj;
        return (o.p1 == p1 && o.p2 == p2) || (o.p1 == p2 && o.p2 == p1);
    }

    public static void getSummarizedStuff(List<BillCollection> billCollections){
        billPairs = new ArrayList<>();

        for(BillCollection bc : billCollections){
            Person p1 = bc.getOwner();
            Iterator<Map.Entry<Person, Double>> entries = bc.getBills().entrySet().iterator();
            while(entries.hasNext()) {
                Map.Entry<Person, Double> next = entries.next();
                Person p2 = next.getKey();
                Double value = next.getValue();
                addNewOrUpdateExisting(p1, p2, value);
            }
        }
    }

    private static void addNewOrUpdateExisting(Person p1, Person p2, double amount){
        for(BillPair e : billPairs){
            if(e.equals(new BillPair(p1, p2))){
                e.setAmountP2ToP1(amount);
                return;
            }
        }
        BillPair newBillPair = new BillPair(p1, p2);
        newBillPair.setAmountP1ToP2(amount);
        billPairs.add(newBillPair);
    }

    public static BillPair getBillPairForPersons(Person p1, Person p2){
        Optional<BillPair> first = billPairs.stream()
                .filter(e -> e.equals(new BillPair(p1, p2)))
                .findFirst();
        return first.orElse(null);
    }
}
