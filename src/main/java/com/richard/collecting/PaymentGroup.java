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

    @Override
    public boolean equals(Object obj) {

        if(obj == null) return false;

        if(!(obj instanceof PaymentGroup)) return false;

        PaymentGroup paymentGroup = (PaymentGroup) obj;

        if(this.getPersonList().containsAll((Collection<?>) paymentGroup.getPersonList())
                && ((Collection<?>) paymentGroup.getPersonList()).containsAll(this.getPersonList())){
            return true;
        }

        return false;
    }
}
