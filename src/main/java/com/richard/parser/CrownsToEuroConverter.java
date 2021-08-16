package com.richard.parser;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CrownsToEuroConverter {

    // 1 Eur = 25.43 Kc
    public final static double conversionRate = 0.0393;

    public static double crownsToEuros(double crowns){
        double euros = conversionRate * crowns;
        return new BigDecimal(euros).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
