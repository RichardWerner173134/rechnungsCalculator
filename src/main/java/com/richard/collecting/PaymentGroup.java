package com.richard.collecting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentGroup implements IPaymentGroup {
    private String paymentGroupTitle;
    private List<Person> personList;

    @Override
    public void addToPaymentGroup(Person person) {

    }

    @Override
    public void removeFromPaymentGroup(Person person) {

    }
}
