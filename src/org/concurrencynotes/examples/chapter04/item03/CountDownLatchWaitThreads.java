package org.concurrencynotes.examples.chapter04.item03;

import java.util.concurrent.CountDownLatch;


public class CountDownLatchWaitThreads {

	public static class ProcessThread implements Runnable {

		CountDownLatch latch;
		CountDownLatch startGate;
		long workDuration;
		String name;

		public ProcessThread(String name, CountDownLatch latch, CountDownLatch startGate, long duration){
			this.name = name;
			this.latch = latch;
			this.startGate = startGate;
			this.workDuration = duration;
		}


		public void run() {

			try {
				startGate.await();
				System.out.println(name +" sleep for "+ workDuration/1000 + " seconds");
				Thread.sleep(workDuration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				//when task finished.. count down the latch count...
				latch.countDown();
			}
			System.out.println(name + " completed its works");
		}
	}

	public static void main(String[] args) {
		// Parent thread creating a latch object
		final CountDownLatch latch = new CountDownLatch(3);
		final CountDownLatch startGate = new CountDownLatch(1);

		new Thread(new ProcessThread("Thread1", latch, startGate, 2000)).start(); // time in millis.. 2 secs
		new Thread(new ProcessThread("Thread2", latch, startGate, 6000)).start();//6 secs
		new Thread(new ProcessThread("Thread3", latch, startGate, 4000)).start();//4 secs

		startGate.countDown();
		System.out.println("Gate open. Waiting for Children processes to complete....");
		try {
			// current thread will get notified if all chidren's are done
			// and thread will resume from wait() mode.
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("All Process Completed....");
		System.out.println("Parent Thread Resuming work....");
	}
}