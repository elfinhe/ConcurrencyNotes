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

public class GoMake<T> {
    public GoChanBuf<T> make(final int size) {
        return new GoChanBuf<T>(size);
    }

    public GoChanUnbuf make() {
        return new GoChanUnbuf<T>();
    }
}


