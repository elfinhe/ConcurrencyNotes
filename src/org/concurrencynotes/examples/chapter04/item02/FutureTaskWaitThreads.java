package org.concurrencynotes.examples.chapter04.item02;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class FutureTaskWaitThreads {
	static final AtomicInteger count = new AtomicInteger(0);
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(8);
		ArrayList<Future<Integer>> futures = new ArrayList<Future<Integer>>();
		for (int i = 0; i < 20; i++) {
			final int threadID = i;
			Callable<Integer> task = () -> {
				try {
					System.err.println("count: " + count.getAndIncrement());
					TimeUnit.SECONDS.sleep(1);
					return threadID;
				}
				catch (InterruptedException e) {
					throw new IllegalStateException("task interrupted", e);
				}
			};
			FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
			executor.submit(task);
			/* Another way to execute Task
			Thread thread = new Thread(futureTask);
			thread.start();
			*/
			Future<Integer> future = futureTask;
			futures.add(future);
		}
		futures.forEach(future -> {
			try {
				System.out.println(future.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		});
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.SECONDS);
	}
}


