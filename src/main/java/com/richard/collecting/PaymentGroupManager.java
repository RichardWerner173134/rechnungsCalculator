package com.richard.collecting;

import java.util.ArrayList;
import java.util.List;

public class PaymentGroupManager {
    private List<PaymentGroup> paymentGroups;

    public PaymentGroupManager(){
        paymentGroups = new ArrayList<>();
    }

    public PaymentGroup getOrCreatePaymentGroup(List<Person> personList){
        for(PaymentGroup pg : paymentGroups){
            if(pg.isSamePaymentGroup(personList)){
                return pg;
            }
        }
        PaymentGroup newPaymentGroup = new PaymentGroup("");
        newPaymentGroup.addToPaymentGroup(personList);
        paymentGroups.add(newPaymentGroup);
        return newPaymentGroup;

    }



}
