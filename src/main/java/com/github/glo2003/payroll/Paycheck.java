package com.github.glo2003.payroll;

public class Paycheck {
    private String recipient;
    private float amount;

    public Paycheck(String recipient, float amount) {
        this.recipient = recipient;
        this.amount = amount;
    }

    public String getRecipient() {
        return recipient;
    }

    public float getAmount() {
        return amount;
    }
}
