package org.concurrencynotes.examples.chapter02.item04;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CreateNewThreadExecutorFuture {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(8);
		ArrayList<Future<Integer>> futures = new ArrayList<Future<Integer>>();
		for (int i = 0; i < 20; i++) {
			Callable<Integer> task = () -> {
				try {
					TimeUnit.SECONDS.sleep(1);
					return 123;
				}
				catch (InterruptedException e) {
					throw new IllegalStateException("task interrupted", e);
				}
			};
			Future<Integer> future = executor.submit(task);
			futures.add(future);
		}
		futures.forEach(f -> {
			try {
				System.out.println(f.get());
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


