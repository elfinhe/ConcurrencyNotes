package org.concurrencynotes.examples.chapter03.item02;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SharingContainerSync {
	static int count = 0;
	static Map<Integer, Integer> container = new HashMap<>();
	static Random random = new Random();
	static Object mutex = new Object();

	static {
		IntStream.range(0, 1000000).forEach(i -> write());
		System.err.println("End Initialization");
	}

	static Integer getRandom() {
		return random.nextInt(100);
	}

	static Integer read() {
		synchronized (mutex) {
			count++;
			return container.getOrDefault(getRandom(), 0);
		}
	}

	static void write() {
		synchronized (mutex) {
			count++;
			container.put(getRandom(), getRandom());
		}
	}

	public static void main(String[] args) throws InterruptedException {
		System.err.println("Start main");
		ExecutorService executor = Executors.newFixedThreadPool(8);
		IntStream.range(0, 10000)
				 .forEach(i -> executor.submit((i%2 == 1) ? SharingContainerSync::read : SharingContainerSync::write ));

		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.SECONDS);
		System.out.println(count);
	}
}


