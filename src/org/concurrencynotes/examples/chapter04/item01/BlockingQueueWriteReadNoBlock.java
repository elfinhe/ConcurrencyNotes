package org.concurrencynotes.examples.chapter04.item01;

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

public class BlockingQueueWriteReadNoBlock {
    static Random random = new Random();
    static final AtomicInteger cnt = new AtomicInteger(0);
    static BlockingQueue<Integer> pipeline = new ArrayBlockingQueue<>(1024);

    static Integer getRandom() {
        return random.nextInt(100);
    }

    static Integer read() {
        Integer current = pipeline.poll();
        if (current != null) {
            System.err.println(cnt.incrementAndGet());
        }
        return current;
    }

    static void write() {
        pipeline.offer(getRandom());
    }

    public static void main(String[] args) throws InterruptedException {
        System.err.println("Start main");
        ExecutorService executor = Executors.newFixedThreadPool(8);
        IntStream.range(0, 10000) // Blocking, starvation
                 .forEach(i -> executor.submit((i%3 == 1) ? BlockingQueueWriteReadNoBlock::write : BlockingQueueWriteReadNoBlock::read));

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
    }
}


