package org.concurrencynotes.examples.chapter03.item01;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

// NOT THREAD SAFE
public class SharingIntegerIncrementVolatile {
	static volatile int count = 0;

	static void incrementSync() {
		count = count + 1;
	}

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(8);
		IntStream.range(0, 10000)
				 .forEach(i -> executor.submit(SharingIntegerIncrementVolatile::incrementSync));

		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.SECONDS);
		System.out.println(count);
	}
}
