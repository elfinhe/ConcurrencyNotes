package org.concurrencynotes.examples.chapter02.item03;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CreateNewThreadExecutor {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(8);
		for (int i = 0; i < 20; i++) {
			executor.submit(() -> {
				String threadName = Thread.currentThread().getName();
				System.out.println("Hello " + threadName);
			});
		}
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.SECONDS);
	}
}


