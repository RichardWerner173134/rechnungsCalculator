package com.richard.calculating;

import com.richard.collecting.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BillCollection {

    private Person owner;
    private Map<Person, Double> bills;
    private List<String> descriptions;

    public BillCollection(Person person){
        owner = person;
        bills = new HashMap<>();
        descriptions = new ArrayList<>();
    }

    public void addPayment(Person creditor, double amount, String description){
        if(bills.containsKey(creditor)){
            Double oldVal = bills.get(creditor);
            bills.put(creditor, oldVal + amount);
        } else {
            bills.put(creditor, amount);
        }
        descriptions.add(description);
    }

    public void removeBillForPerson(Person p){
        bills.remove(p);
    }

    public void reduceBillForPerson(Person p, double amount){
        Double oldVal = bills.get(p);
        bills.put(p, oldVal - amount);
    }
}
