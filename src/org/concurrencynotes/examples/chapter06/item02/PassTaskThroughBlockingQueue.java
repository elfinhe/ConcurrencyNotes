package org.concurrencynotes.examples.chapter06.item02;

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

public class PassTaskThroughBlockingQueue {
    static Random random = new Random();
    static final AtomicInteger cnt = new AtomicInteger(0);
    static BlockingQueue<Integer> pipeline = new ArrayBlockingQueue<>(1024);
    static BlockingQueue<Runnable> taskPipeline = new ArrayBlockingQueue<>(1024);

    static Integer getRandom() {
        return random.nextInt(100);
    }

    public static void main(String[] args) throws InterruptedException {
        System.err.println("Start main");
        ExecutorService executor = Executors.newFixedThreadPool(8);
        IntStream.range(0, 100)
                 .forEach(i -> {
                     if (i%2 == 0) {
                         try {
                             taskPipeline.put(() -> { // put write
                                 try {
                                     pipeline.put(getRandom());
                                 } catch (InterruptedException e) {
                                     e.printStackTrace();
                                 }
                             });
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                         }
                     } else {
                         try {
                             taskPipeline.put(() -> { // put read
                                 try {
                                     pipeline.take();
                                     System.err.println(cnt.incrementAndGet());
                                 } catch (InterruptedException e) {
                                     e.printStackTrace();
                                 }
                             });
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                         }
                     }
                 });

        new Thread(() -> {
            for (Runnable task : taskPipeline) {
                executor.submit(task);
            }
        }).start();


        TimeUnit.SECONDS.sleep(2);
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
    }
}


