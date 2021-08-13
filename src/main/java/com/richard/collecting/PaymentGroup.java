package com.richard.collecting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentGroup implements IPaymentGroup {
    private String paymentGroupTitle;
    private List<Person> personList;

    public PaymentGroup(String paymentGroupTitle){
        this.paymentGroupTitle = paymentGroupTitle;
        personList = new ArrayList<>();
    }

    @Override
    public void addToPaymentGroup(Person person) {
        personList.add(person);
    }

    public void addToPaymentGroup(List<Person> personList){
        this.personList.addAll(personList);
    }

    @Override
    public void removeFromPaymentGroup(Person person) {
        personList.remove(person);
    }

    public boolean isSamePaymentGroup(List<Person> persons){
        return persons.containsAll(this.personList) && this.personList.containsAll(persons);
    }

    public boolean isSamePaymentGroup(PaymentGroup paymentGroup){
        return paymentGroup.getPersonList().containsAll(this.personList) && this.personList.containsAll(paymentGroup.getPersonList());
    }

}
