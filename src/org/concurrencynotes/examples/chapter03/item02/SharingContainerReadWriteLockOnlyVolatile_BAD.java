package org.concurrencynotes.examples.chapter03.item02;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

public class SharingContainerReadWriteLockOnlyVolatile_BAD {
	static volatile int count = 0;
	static Map<Integer, Integer> container = new HashMap<>();
	static Random random = new Random();
	static ReadWriteLock lock = new ReentrantReadWriteLock();

	static {
		IntStream.range(0, 1000000).forEach(i -> write());
		System.err.println("End Initialization");
	}

	static Integer getRandom() {
		return random.nextInt(100);
	}

	static Integer read() {
		lock.readLock().lock();
		try {
			count++;
			return container.getOrDefault(getRandom(), 0);
		} finally {
			lock.readLock().unlock();
		}
	}

	static void write() {
		lock.writeLock().lock();
		try {
			count++;
			container.put(getRandom(), getRandom());
		} finally {
			lock.writeLock().unlock();
		}

	}

	public static void main(String[] args) throws InterruptedException {
		System.err.println("Start main");
		ExecutorService executor = Executors.newFixedThreadPool(8);
		IntStream.range(0, 10000)
				 .forEach(i -> executor.submit((i%2 == 1) ? SharingContainerReadWriteLockOnlyVolatile_BAD::read : SharingContainerReadWriteLockOnlyVolatile_BAD::write ));

		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.SECONDS);
		System.out.println(count);
	}
}


