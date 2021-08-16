package com.richard.parser;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TransactionListDO {
    private TransactionDO[] transactions;
}
