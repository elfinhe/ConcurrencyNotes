package org.concurrencynotes.examples.chapter03.item03;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SharingSingleton {

	public static void main(String[] args) throws InterruptedException {
		System.err.println("Start main");
		ExecutorService executor = Executors.newFixedThreadPool(8);
		IntStream.range(0, 100)
				 .forEach(i -> executor.submit( () -> ASingleton01.getInstance()));
		IntStream.range(0, 100)
				 .forEach(i -> executor.submit( () -> ASingleton02.getInstance()));
		IntStream.range(0, 100)
				 .forEach(i -> executor.submit( () -> ASingleton03.getInstance()));

		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.SECONDS);
		System.out.println();
	}
}


class ASingleton01 {

	private static volatile ASingleton01 instance = null;
	private static Object mutex = new Object();

	private ASingleton01() {
	}

	public static ASingleton01 getInstance() {
		if (instance == null) {
			synchronized (mutex) {
				if (instance == null) instance = new ASingleton01();
			}
		}
		return instance;
	}
}

class ASingleton02 {

	private static ASingleton02 instance = null;

	private ASingleton02() {
	}

	synchronized public static ASingleton02 getInstance() {
		if (instance == null) instance = new ASingleton02();
		return instance;
	}
}

class ASingleton03 {

	private static ASingleton03 instance = new ASingleton03();

	private ASingleton03() {
	}

	synchronized public static ASingleton03 getInstance() {
		return instance;
	}
}