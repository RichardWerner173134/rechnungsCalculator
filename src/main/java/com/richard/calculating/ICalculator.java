package com.richard.calculating;

import com.richard.collecting.InvoiceOverview;
import com.richard.collecting.Person;

import java.util.List;
import java.util.Map;

public interface ICalculator {
    List<BillCollection> calculateAllBills();
}
