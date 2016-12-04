package org.concurrencynotes.examples.chapter03.item02;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SharingContainerSyncCOW {
	static int count = 0;
	static volatile Map<Integer, Integer> containerToBeRead = new HashMap<>();
	static Map<Integer, Integer> containerToBeWritten = new HashMap<>();
	static int threshold = 0;
	static final int LIMIT = 100;
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
		count++;
		return containerToBeRead.getOrDefault(getRandom(), 0);
	}

	static void write() {
		count++;
		threshold++;
		containerToBeWritten.put(getRandom(), getRandom());

		if (threshold > LIMIT) {
			synchronized (mutex) {
				if (threshold > LIMIT) {
					containerToBeRead = containerToBeWritten;
					containerToBeWritten = new HashMap<>(containerToBeRead);
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		System.err.println("Start main");
		ExecutorService executor = Executors.newFixedThreadPool(8);
		IntStream.range(0, 10000)
				 .forEach(i -> executor.submit((i%2 == 1) ? SharingContainerSyncCOW::read : SharingContainerSyncCOW::write ));

		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.SECONDS);
		System.out.println(count);
	}
}


