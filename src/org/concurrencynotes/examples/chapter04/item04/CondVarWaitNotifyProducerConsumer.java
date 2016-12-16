package org.concurrencynotes.examples.chapter04.item04;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CondVarWaitNotifyProducerConsumer {
	public static void main(String[] args) {
		Drop drop = new Drop();
		(new Thread(new Producer(drop))).start();
		(new Thread(new Consumer(drop))).start();
	}
}

class Consumer implements Runnable {
	private Drop drop;

	public Consumer(Drop drop) {
		this.drop = drop;
	}

	public void run() {
		Random random = new Random();
		for (String message = drop.take();
			 ! message.equals("DONE");
			 message = drop.take()) {
			System.out.format("MESSAGE RECEIVED: %s%n", message);
			try {
				Thread.sleep(random.nextInt(5000));
			} catch (InterruptedException e) {}
		}
	}
}

class Producer implements Runnable {
	private Drop drop;

	public Producer(Drop drop) {
		this.drop = drop;
	}

	public void run() {
		String importantInfo[] = {
				"Mares eat oats",
				"Does eat oats",
				"Little lambs eat ivy",
				"A kid will eat ivy too"
		};
		Random random = new Random();

		for (int i = 0;
			 i < importantInfo.length;
			 i++) {
			drop.put(importantInfo[i]);
			try {
				Thread.sleep(random.nextInt(5000));
			} catch (InterruptedException e) {}
		}
		drop.put("DONE");
	}
}


class Drop {
	// Message sent from producer
	// to consumer.
	private String message;
	// True if consumer should wait
	// for producer to send message,
	// false if producer should wait for
	// consumer to retrieve message.
	private boolean empty = true;

	public synchronized String take() {
		// Wait until message is
		// available.
		while (empty) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		// Toggle status.
		empty = true;
		// Notify producer that
		// status has changed.
		notifyAll();
		return message;
	}

	public synchronized void put(String message) {
		// Wait until message has
		// been retrieved.
		while (!empty) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		// Toggle status.
		empty = false;
		// Store message.
		this.message = message;
		// Notify consumer that status
		// has changed.
		notifyAll();
	}
}


// Condition factors out the Object monitor methods (wait, notify and notifyAll) into distinct objects to give the
// effect of having multiple wait-sets per object, by combining them with the use of arbitrary Lock implementations.
// Where a Lock replaces the use of synchronized methods and statements, a Condition replaces the use of the Object
// monitor methods.

class BoundedBuffer {
	final Lock lock = new ReentrantLock();
	final Condition notFull  = lock.newCondition();
	final Condition notEmpty = lock.newCondition();

	final Object[] items = new Object[100];
	int putptr, takeptr, count;

	public void put(Object x) throws InterruptedException {
		lock.lock();
		try {
			while (count == items.length)
				notFull.await();
			items[putptr] = x;
			if (++putptr == items.length) putptr = 0;
			++count;
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	public Object take() throws InterruptedException {
		lock.lock();
		try {
			while (count == 0)
				notEmpty.await();
			Object x = items[takeptr];
			if (++takeptr == items.length) takeptr = 0;
			--count;
			notFull.signal();
			return x;
		} finally {
			lock.unlock();
		}
	}
}

