package com.rfa.n26.transactions.model;

import java.sql.Timestamp;
import java.util.Date;

public class Transaction implements Comparable<Transaction>{

    private double amount;
    private long timestamp;

    public Transaction() {}
    public Transaction(double amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String toString() {
        return "amount: " + amount + ", timestamp: " + timestamp;
    }

    public int compareTo(Transaction transaction) {
        return Double.compare(this.amount, transaction.getAmount());
    }
}
