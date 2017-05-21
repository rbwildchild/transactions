package com.rfa.n26.transactions.buffer;

import static org.junit.Assert.*;

import com.rfa.n26.transactions.model.Transaction;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CircularTransactionStatsBufferTest {

    @Test
    public void getIndexTest() {
        CircularTransactionStatsBuffer buffer = new CircularTransactionStatsBuffer();
        assertEquals(12, buffer.getIndex(1495398972904L));
        assertEquals(15, buffer.getIndex(1495399035143L));
        assertEquals(28, buffer.getIndex(1495399048112L));
        assertEquals(37, buffer.getIndex(1495399057580L));
        assertEquals(55, buffer.getIndex(1495399075230L));
    }

    @Test
    public void updateStatsTest1() {
        CircularTransactionStatsBuffer buffer = new CircularTransactionStatsBuffer();
        assertEquals(1, buffer.getIndex(1495399381904L));
        buffer.putTransaction(new Transaction(2, 1495399381904L)); // Index 1
        assertEquals(1, buffer.getCurrentStats().getCount());
        assertEquals(2, buffer.getCurrentStats().getSum(), 0);
        assertEquals(2, buffer.getCurrentStats().getAvg(), 0);
        assertEquals(2, buffer.getCurrentStats().getMax(), 0);
        assertEquals(2, buffer.getCurrentStats().getMin(), 0);
        assertEquals(11, buffer.getIndex(1495399391904L));
        buffer.putTransaction(new Transaction(3, 1495399391904L)); // Index 11
        assertEquals(2, buffer.getCurrentStats().getCount());
        assertEquals(5, buffer.getCurrentStats().getSum(), 0);
        assertEquals(2.5, buffer.getCurrentStats().getAvg(), 0);
        assertEquals(3, buffer.getCurrentStats().getMax(), 0);
        assertEquals(2, buffer.getCurrentStats().getMin(), 0);
        assertEquals(51, buffer.getIndex(1495399431904L));
        buffer.putTransaction(new Transaction(4, 1495399431904L)); // Index 51
        assertEquals(3, buffer.getCurrentStats().getCount());
        assertEquals(9, buffer.getCurrentStats().getSum(), 0);
        assertEquals(3, buffer.getCurrentStats().getAvg(), 0);
        assertEquals(4, buffer.getCurrentStats().getMax(), 0);
        assertEquals(2, buffer.getCurrentStats().getMin(), 0);
    }

    @Test
    public void updateStatsTest2() {
        CircularTransactionStatsBuffer buffer = new CircularTransactionStatsBuffer();
        assertEquals(1, buffer.getIndex(1495399381904L));
        buffer.putTransaction(new Transaction(2, 1495399381904L)); // Index 1
        assertEquals(1, buffer.getCurrentStats().getCount());
        assertEquals(2, buffer.getCurrentStats().getSum(), 0);
        assertEquals(2, buffer.getCurrentStats().getAvg(), 0);
        assertEquals(2, buffer.getCurrentStats().getMax(), 0);
        assertEquals(2, buffer.getCurrentStats().getMin(), 0);
        assertEquals(11, buffer.getIndex(1495399391904L));
        buffer.putTransaction(new Transaction(3, 1495399391904L)); // Index 11
        assertEquals(2, buffer.getCurrentStats().getCount());
        assertEquals(5, buffer.getCurrentStats().getSum(), 0);
        assertEquals(2.5, buffer.getCurrentStats().getAvg(), 0);
        assertEquals(3, buffer.getCurrentStats().getMax(), 0);
        assertEquals(2, buffer.getCurrentStats().getMin(), 0);
        assertEquals(51, buffer.getIndex(1495399431904L));
        buffer.putTransaction(new Transaction(4, 1495399431904L)); // Index 51
        assertEquals(3, buffer.getCurrentStats().getCount());
        assertEquals(9, buffer.getCurrentStats().getSum(), 0);
        assertEquals(3, buffer.getCurrentStats().getAvg(), 0);
        assertEquals(4, buffer.getCurrentStats().getMax(), 0);
        assertEquals(2, buffer.getCurrentStats().getMin(), 0);
        assertEquals(5, buffer.getIndex(1495399445904L));
        buffer.putTransaction(new Transaction(5, 1495399445904L)); // Index 5
        assertEquals(3, buffer.getCurrentStats().getCount());
        assertEquals(12, buffer.getCurrentStats().getSum(), 0);
        assertEquals(4, buffer.getCurrentStats().getAvg(), 0);
        assertEquals(5, buffer.getCurrentStats().getMax(), 0);
        assertEquals(3, buffer.getCurrentStats().getMin(), 0);

        assertEquals(34, buffer.getIndex(1495409734939L));
        buffer.putTransaction(new Transaction(10, 1495409734939L)); // Index 5
        assertEquals(1, buffer.getCurrentStats().getCount());
        assertEquals(10, buffer.getCurrentStats().getSum(), 0);
        assertEquals(10, buffer.getCurrentStats().getAvg(), 0);
        assertEquals(10, buffer.getCurrentStats().getMax(), 0);
        assertEquals(10, buffer.getCurrentStats().getMin(), 0);
    }
}
