package org.concurrencynotes.examples.chapter05.item03;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;


//         	  Exception   	Special Value	Blocks 	Times Out
//  Insert	  add(o)        offer(o)    	put(o)	offer(o, timeout, timeunit)
//  Remove	  remove(o)   	poll()	        take()	poll(timeout, timeunit)
//  Examine	  element()   	peek()

// ArrayBlockingQueue
// DelayQueue
// LinkedBlockingQueue
// PriorityBlockingQueue
// SynchronousQueue

public class GoChanBuf<T> implements GoChan<T> {

    BlockingQueue<T> chan;

    public GoChanBuf(final int size) {
        chan = new ArrayBlockingQueue<T>(size);
    }

    @Override
    public void send(final T t) throws InterruptedException {
        chan.put(t);
    }

    @Override
    public T recv() throws InterruptedException {
        return chan.take();
    }

    @Override
    public T peek() {
        return chan.peek();
    }
}


