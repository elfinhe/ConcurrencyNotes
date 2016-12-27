package org.concurrencynotes.examples.chapter05.item03;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


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


