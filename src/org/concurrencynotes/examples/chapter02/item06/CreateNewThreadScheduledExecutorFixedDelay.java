package org.concurrencynotes.examples.chapter02.item06;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CreateNewThreadScheduledExecutorFixedDelay {

	static Callable<String> callable(String result, long sleepSeconds) {
		return () -> {
			TimeUnit.SECONDS.sleep(sleepSeconds);
			return result;
		};
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

		Runnable task = () -> {
			try {
				TimeUnit.SECONDS.sleep(2);
				System.out.println("Scheduling: " + System.nanoTime());
			}
			catch (InterruptedException e) {
				System.err.println("task interrupted");
			}
		};

		executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
	}
}


