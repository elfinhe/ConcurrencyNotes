package org.concurrencynotes.examples.chapter02.item5;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CreateNewThreadExecutorInvokeAll {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(8);
		List<Callable<String>> callables = Arrays.asList(
				() -> "task1",
				() -> "task2",
				() -> "task3");
		executor.invokeAll(callables)
				.stream()
				.map(future -> {
					try {
						return future.get();
					}
					catch (Exception e) {
						throw new IllegalStateException(e);
					}
				})
				.forEach(System.out::println);
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.SECONDS);
	}
}


