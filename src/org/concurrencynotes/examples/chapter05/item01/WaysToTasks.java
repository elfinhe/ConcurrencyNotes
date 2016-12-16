package org.concurrencynotes.examples.chapter05.item01;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class WaysToTasks {
	static final AtomicInteger count = new AtomicInteger(0);
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(8);
		ArrayList<Future<Integer>> futures = new ArrayList<Future<Integer>>();
		for (int i = 0; i < 20; i++) {
            final Integer threadID = i;
            // Since Runnable is a functional interface we can utilize Java 8 lambda expressions to print the current
            // threads name to the console.
			Callable<Integer> task = () -> {
				try {
					System.err.println("count: " + count.getAndIncrement());
					TimeUnit.SECONDS.sleep(1);
					return threadID; // local variables used in lambda should be final or effectively final (no change)
				}
				catch (InterruptedException e) {
					throw new IllegalStateException("task interrupted", e);
				}
			};

            Future<Integer> future   = startThread(task);
			Future<Integer> future01 = submitToExecutor(executor, task);
			Future<Integer> future02 = submitToExecutor(executor, () -> 1 + 1);
			Future<Integer> future03 = submitToExecutor(executor, () -> 1 + 1 + threadID);

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

    private static Future<Integer> submitToExecutor(final ExecutorService executor, final Callable<Integer> task) {
        return executor.submit(task);
    }

    private static Future<Integer> submitToExecutor(final ExecutorService executor, final FutureTask<Integer> futureTask) {
        executor.submit(futureTask);
        return futureTask;
    }

	@NotNull
	private static Future<Integer> startThread(final Callable<Integer> task) {
		FutureTask<Integer> futureTask = new FutureTask<>(task);
		Thread thread = new Thread(futureTask);
		thread.start();
		return futureTask;
	}
}


