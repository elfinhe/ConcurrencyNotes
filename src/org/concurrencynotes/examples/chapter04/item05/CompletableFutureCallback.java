package org.concurrencynotes.examples.chapter04.item05;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CompletableFutureCallback {
    private static Random rand = new Random();
    private static long t = System.currentTimeMillis();
    static int getMoreData() {
        System.out.println("Beg getMoreData()");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("End getMoreData(). Time passed: " + (System.currentTimeMillis() - t)/1000 + " seconds");
        System.out.println(Thread.currentThread().getName());
        return rand.nextInt(1000);
    }
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(CompletableFutureCallback::getMoreData, executor);
        System.out.println(Thread.currentThread().getName());
        Future<Integer> f = future.whenComplete((v, e) -> {
            System.out.println("Callback print v: " + v);
            System.out.println("Callback print e: " + e);
            System.out.println(Thread.currentThread().getName());
        });
        System.out.println("After future.whenComplete");
        System.out.println(f.get() + " f.get()");
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
    }
}
