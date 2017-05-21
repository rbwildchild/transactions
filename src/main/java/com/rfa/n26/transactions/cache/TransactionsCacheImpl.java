package com.rfa.n26.transactions.cache;

import static com.rfa.n26.transactions.config.Constants.TIMEFRAME;
import static com.rfa.n26.transactions.model.TransactionStorageStatus.*;

import com.rfa.n26.transactions.buffer.CircularTransactionStatsBuffer;
import com.rfa.n26.transactions.buffer.TransactionStatsBuffer;
import com.rfa.n26.transactions.model.Transaction;
import com.rfa.n26.transactions.model.TransactionStats;
import com.rfa.n26.transactions.model.TransactionStorageStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionsCacheImpl implements TransactionsCache{

    static private final Logger log = LoggerFactory.getLogger(TransactionsCacheImpl.class);

    private TransactionStatsBuffer transactionStatsBuffer;

    public TransactionsCacheImpl() {
        transactionStatsBuffer = new CircularTransactionStatsBuffer();
    }

    public TransactionStorageStatus addTransaction(Transaction transaction){
        long current = System.currentTimeMillis();
        long time = transaction.getTimestamp();
        if(current - time <= TIMEFRAME) {
            transactionStatsBuffer.putTransaction(transaction);
            return OK;
        } else
            return DELAYED;
    }

    public TransactionStats getTransactionStats(){
        return transactionStatsBuffer.getCurrentStats();
    }

}
