package com.rfa.n26.transactions.holder;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.rfa.n26.transactions.cache.TransactionsCache;
import com.rfa.n26.transactions.cache.TransactionsCacheImpl;
import com.rfa.n26.transactions.exception.TransactionHolderException;
import com.rfa.n26.transactions.model.Transaction;
import com.rfa.n26.transactions.model.TransactionStats;
import com.rfa.n26.transactions.model.TransactionStorageStatus;
import com.rfa.n26.transactions.task.StatsTask;
import org.jvnet.hk2.config.Transactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import static com.rfa.n26.transactions.model.TransactionStorageStatus.FAIL;

@Component
@Scope("singleton")
public class TransactionHolder {

    static private final Logger log = LoggerFactory.getLogger(TransactionHolder.class);

    @Autowired
    private TransactionsCache transactionsCache;

    public TransactionStorageStatus saveTransaction(Transaction transaction) throws TransactionHolderException {
        try {
            return transactionsCache.addTransaction(transaction);
        } catch(Exception e){
            log.error("Error saving transaction.", e);
            throw new TransactionHolderException("Error saving transaction.", e);
        }
    }

    public TransactionStats getTransactionStats () {
        return transactionsCache.getTransactionStats();
    }
}
