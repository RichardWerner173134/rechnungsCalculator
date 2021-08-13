package com.richard.collecting;

import java.util.ArrayList;
import java.util.List;

public class PaymentGroupManager {
    private List<PaymentGroup> paymentGroups;

    public PaymentGroupManager(){
        paymentGroups = new ArrayList<>();
    }

    public PaymentGroup getOrCreatePaymentGroup(List<Person> personList){
        for(PaymentGroup p : paymentGroups){
            if(p.equals(personList)){
                return p;
            }
        }
        PaymentGroup newPaymentGroup = new PaymentGroup("");
        newPaymentGroup.addToPaymentGroup(personList);
        return newPaymentGroup;

    }



}
