package com.rfa.n26.transactions.buffer;

import com.rfa.n26.transactions.model.Transaction;
import com.rfa.n26.transactions.model.TransactionStats;

import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.rfa.n26.transactions.config.Constants.TIMEFRAME;

public class CircularTransactionStatsBuffer implements TransactionStatsBuffer {

    public static final int BUFFER_SIZE = 60;

    private final Lock lock;
    private TransactionStats[] transactionStatsBuffer;
    private TransactionStats transactionStats;
    private int lastIndex;

    public CircularTransactionStatsBuffer() {
        lock = new ReentrantLock();
        transactionStats = new TransactionStats();
        transactionStatsBuffer = new TransactionStats[BUFFER_SIZE];
        lastIndex = -1;
        for(int i = 0;i < BUFFER_SIZE;i++){
            transactionStatsBuffer[i] = new TransactionStats();
        }
    }

    public int putTransaction(Transaction transaction){
        int index = (int) getIndex(transaction.getTimestamp());
        updateStats(index, transaction);
        return index;
    }

    public void updateStats(int index, Transaction transaction) {
        if(lastIndex < 0)
            lastIndex = index;
        TransactionStats lastTransactionStats = transactionStatsBuffer[lastIndex];
        long current = transaction.getTimestamp();
        long lastTime = lastTransactionStats.getTimestamp();
        if(lastTime > 0 && current - lastTime > TIMEFRAME) {
            lastIndex = index + 1;
            scrub(lastIndex, index);
            transactionStats.setCount(0);
            transactionStats.setSum(0);
            transactionStats.setAvg(0);
            transactionStats.setMax(0);
            transactionStats.setMin(0);
        }
        else
            scrub(lastIndex, index);
        calculateDeltas(lastIndex, index, transaction);
        transactionStats.setCount(transactionStats.getCount() + 1);
        transactionStats.setSum(transactionStats.getSum() + transaction.getAmount());
        transactionStats.setAvg(transactionStats.getSum() / transactionStats.getCount());
        transactionStats.setMax(transactionStats.getMax() > 0 ? Double.max(transactionStats.getMax(), transaction.getAmount()) : transaction.getAmount());
        transactionStats.setMin(transactionStats.getMin() > 0 ? Double.min(transactionStats.getMin(), transaction.getAmount()) : transaction.getAmount());
        lastIndex = index;
    }

    public void calculateDeltas(int lastIndex, int index, Transaction transaction) {
        TransactionStats currTransactionStats = transactionStatsBuffer[index];
        currTransactionStats.setTimestamp(transaction.getTimestamp());
        currTransactionStats.setCount(currTransactionStats.getCount() + 1);
        currTransactionStats.setSum(currTransactionStats.getSum() + transaction.getAmount());
        currTransactionStats.setMax(currTransactionStats.getMax() > 0 ? Double.max(currTransactionStats.getMax(), transaction.getAmount()): transaction.getAmount());
        currTransactionStats.setMin(currTransactionStats.getMin() > 0 ? Double.min(currTransactionStats.getMin(), transaction.getAmount()): transaction.getAmount());
    }

    private void scrub(int lastIndex, int index){
        int finalIndex = lastIndex > index ? BUFFER_SIZE + index : index;
        long count = transactionStats.getCount();
        double sum = transactionStats.getSum();
        for(int i = lastIndex + 1;i < finalIndex; i++) {
            TransactionStats currTransactionStats = transactionStatsBuffer[i % BUFFER_SIZE];
            count -= currTransactionStats.getCount();
            sum -= currTransactionStats.getSum();
            currTransactionStats.setCount(0);
            currTransactionStats.setSum(0);
            currTransactionStats.setMax(0);
            currTransactionStats.setMin(0);
        }
        finalIndex = lastIndex < index ? BUFFER_SIZE + lastIndex : lastIndex;
        double max = transactionStats.getMax();
        double min = transactionStats.getMin();
        for(int i = index + 1;i < finalIndex; i++) {
            TransactionStats currTransactionStats = transactionStatsBuffer[i % BUFFER_SIZE];
            if(currTransactionStats.getMax() > 0 && currTransactionStats.getMax() > max)
                max = currTransactionStats.getMax();
            if(currTransactionStats.getMin() > 0 && currTransactionStats.getMin() > min)
                min = currTransactionStats.getMin();
        }
        transactionStats.setCount(count);
        transactionStats.setSum(sum);
        transactionStats.setAvg(sum / (double) count);
        transactionStats.setMax(max);
        transactionStats.setMin(min);
    }

    public long getIndex(long timestamp){
        return (timestamp / 1000) % BUFFER_SIZE;
    }

    public TransactionStats getCurrentStats() {
        return transactionStats;
    }

}
