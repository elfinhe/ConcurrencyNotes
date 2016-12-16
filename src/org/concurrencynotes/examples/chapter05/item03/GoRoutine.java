package org.concurrencynotes.examples.chapter05.item03;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.function.Function;


//         	  Exception   	Special Value	Blocks 	Times Out
//  Insert	  add(o)        offer(o)    	put(o)	offer(o, timeout, timeunit)
//  Remove	  remove(o)   	poll()	        take()	poll(timeout, timeunit)
//  Examine	  element()   	peek()

// ArrayBlockingQueue
// DelayQueue
// LinkedBlockingQueue
// PriorityBlockingQueue
// SynchronousQueue

public class GoRoutine<T> {
    Future<T> go(Callable<T> func) {
        FutureTask<T> task = new FutureTask<T>(func);
        new Thread(task).start();
        return task;
    }
}


