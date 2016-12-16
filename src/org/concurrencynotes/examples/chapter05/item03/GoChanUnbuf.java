package org.concurrencynotes.examples.chapter05.item03;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
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

public class GoChanUnbuf<T> implements GoChan<T> {

    final BlockingQueue<T> chan = new SynchronousQueue<T>();

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


