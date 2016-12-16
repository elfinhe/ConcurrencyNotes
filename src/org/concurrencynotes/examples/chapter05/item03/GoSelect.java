package org.concurrencynotes.examples.chapter05.item03;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;


//         	  Exception   	Special Value	Blocks 	Times Out
//  Insert	  add(o)        offer(o)    	put(o)	offer(o, timeout, timeunit)
//  Remove	  remove(o)   	poll()	        take()	poll(timeout, timeunit)
//  Examine	  element()   	peek()

// ArrayBlockingQueue
// DelayQueue
// LinkedBlockingQueue
// PriorityBlockingQueue
// SynchronousQueue

public class GoSelect<T> {
    private ArrayList<GoChan<T>> chanList;

    private GoSelect() {};
    public GoSelect(final ArrayList<GoChan<T>> chanList) {
        this.chanList = chanList;
    }

    // Only for ONE thread
    public GoChan<T> select() throws InterruptedException {
        while(true) {
            for (GoChan<T> chan : chanList) {
                if (chan.peek() != null) {
                    return chan;
                }
            }
            TimeUnit.MILLISECONDS.sleep(1);
        }
    }
}


