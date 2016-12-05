package org.concurrencynotes.examples.chapter03.item01;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class SharingIntegerIncrementAtomicCAS {
	static final AtomicInteger count = new AtomicInteger(0);

	static void incrementSync() {
		for (;;) {
			int current = count.get();
			int next = current + 1;
			if (count.compareAndSet(current, next))
				break;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(8);
		IntStream.range(0, 10000)
				 .forEach(i -> executor.submit(SharingIntegerIncrementAtomicCAS::incrementSync));

		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.SECONDS);
		System.out.println(count);
	}
}


