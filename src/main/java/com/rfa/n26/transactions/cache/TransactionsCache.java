package com.rfa.n26.transactions.cache;

import com.rfa.n26.transactions.model.Transaction;
import com.rfa.n26.transactions.model.TransactionStats;
import com.rfa.n26.transactions.model.TransactionStorageStatus;

import java.util.Set;
import java.util.concurrent.locks.Lock;

public interface TransactionsCache {
    TransactionStorageStatus addTransaction(Transaction transaction);
    TransactionStats getTransactionStats();
}
