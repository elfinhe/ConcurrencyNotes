package org.concurrencynotes.examples.chapter03.item01;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class SharingIntegerIncrementAtomic {
	static AtomicInteger count = new AtomicInteger(0);
	static Object mutex = new Object();

	static void incrementSync() {
		synchronized (mutex) {
			count.incrementAndGet();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(8);
		IntStream.range(0, 10000)
				 .forEach(i -> executor.submit(SharingIntegerIncrementAtomic::incrementSync));

		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.SECONDS);
		System.out.println(count);
	}
}


