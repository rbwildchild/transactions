package com.rfa.n26.transactions.buffer;

import com.rfa.n26.transactions.model.Transaction;
import com.rfa.n26.transactions.model.TransactionStats;

public interface TransactionStatsBuffer {
    int putTransaction(Transaction transaction);
    TransactionStats getCurrentStats();
}
