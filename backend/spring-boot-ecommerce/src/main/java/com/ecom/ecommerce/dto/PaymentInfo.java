package com.ecom.ecommerce.dto;

// We will receive this info from the frontend (client)
public class PaymentInfo {

    // e.g. in US dollar, represents in cents
    private int amount;
    private String currency;
    private String receiptEmail;

    public int getAmount() {
        return this.amount;
    }

    public String getCurrency() {
        return this.currency;
    }

    public String getReceiptEmail() { return this.receiptEmail;}
}
