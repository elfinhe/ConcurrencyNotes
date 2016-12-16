package org.concurrencynotes.examples.chapter05.item03;

//         	  Exception   	Special Value	Blocks 	Times Out
//  Insert	  add(o)        offer(o)    	put(o)	offer(o, timeout, timeunit)
//  Remove	  remove(o)   	poll()	        take()	poll(timeout, timeunit)
//  Examine	  element()   	peek()

// ArrayBlockingQueue
// DelayQueue
// LinkedBlockingQueue
// PriorityBlockingQueue
// SynchronousQueue

public interface GoChan <T> {
    void send(final T t) throws InterruptedException;
    T recv() throws InterruptedException;
    T peek();
//    void close();
//    Stream<T> range();
//    int size();
}


