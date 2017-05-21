package com.rfa.n26.transactions.task;

import com.rfa.n26.transactions.cache.TransactionsCache;
import com.rfa.n26.transactions.cache.TransactionsCacheImpl;
import com.rfa.n26.transactions.model.Transaction;
import com.rfa.n26.transactions.model.TransactionStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantLock;

public class StatsTask {

    static private final Logger log = LoggerFactory.getLogger(StatsTask.class);

    @Autowired
    private TransactionsCache transactionsCache;

    private TaskExecutor taskExecutor;

    public StatsTask(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @PostConstruct
    public void init() {
        taskExecutor.execute(new StatsRunnable(transactionsCache));
    }

    static class StatsRunnable implements Runnable {

        private TransactionsCache transactionsCache;

        public StatsRunnable(TransactionsCache transactionsCache) {
            this.transactionsCache = transactionsCache;
        }

        public void run() {
            /* while(true) {
                try {
                    if (transactionsCache.getLock().tryLock()) {
                        Set<Transaction> transactions = transactionsCache.getTransactions();
                        if(transactions.size() > 0) {
                            long current = System.currentTimeMillis();
                            long boundary = current - TransactionsCacheImpl.TIMEFRAME;
                            transactions.removeIf(t -> t.getTimestamp() < boundary);
                            calculateStats(transactionsCache.getTransactions(), transactionsCache.getTransactionStats());
                        }
                    }
                } catch(Exception e){
                    log.error("Error calculating stats.", e);
                } finally {
                    if (((ReentrantLock)transactionsCache.getLock()).isHeldByCurrentThread()) {
                        transactionsCache.getLock().unlock();
                    }
                }
            } */
        }

        private void calculateStats(Set<Transaction> transactions, TransactionStats transactionStats) {
            long count = transactions.size();
            double sum = transactions.stream().reduce(new Double(0), (a, t) -> a + t.getAmount(), (a, t) -> a + t);
            transactionStats.setCount(count);
            transactionStats.setSum(sum);
            transactionStats.setAvg(sum / count);
            transactionStats.setMax(((TreeSet<Transaction>)transactions).first().getAmount());
            transactionStats.setMax(((TreeSet<Transaction>)transactions).last().getAmount());
        }
    }

}
