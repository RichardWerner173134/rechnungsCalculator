package com.richard.calculating;

import com.richard.collecting.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BillCollection {

    private Person owner;
    private Map<Person, Double> bills;

    public BillCollection(Person person){
        owner = person;
        bills = new HashMap<>();
    }

    public void addPayment(Person creditor, double amount){
        if(bills.containsKey(creditor)){
            Double oldVal = bills.get(creditor);
            bills.put(creditor, oldVal + amount);
        } else {
            bills.put(creditor, amount);
        }
    }
}
