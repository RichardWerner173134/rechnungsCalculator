package com.richard.parser;

import com.google.gson.Gson;
import com.richard.collecting.InvoiceOverview;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class InvoiceReader {

    private InvoiceOverview invoiceOverview;

    public InvoiceReader(InvoiceOverview invoiceOverview) {
        this.invoiceOverview = invoiceOverview;
    }

    public InvoiceReader(){
        invoiceOverview = new InvoiceOverview();
    }



    public InvoiceOverview readAndConvertFile() throws IOException {
        String fileContent = readInvoiceFile();
        parseContent(fileContent);
        return null;
    }

    private void parseContent(String fileContent){
        Gson gson = new Gson();

        TransactionListDO transactions = gson.fromJson(fileContent, TransactionListDO.class);
        int x = 0;
    }

    private String readInvoiceFile() throws IOException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("invoices.json");
        InputStreamReader isr = null;
        if (resourceAsStream != null) {
            isr = new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8);
        }

        BufferedReader br = null;
        if (isr != null) {
            br = new BufferedReader(isr);
        }
        String all = "";
        String line;
        if (br != null) {
            while((line = br.readLine()) != null){
                all += line;
            }
        }
        return all;
        //return "{" + all.replaceAll("\"", "\\\\\"") + "}";
    }
}