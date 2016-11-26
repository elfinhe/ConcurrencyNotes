package org.concurrencynotes.examples.chapter02.item01;

public class CreateNewThread {
	public static void main(String[] args) {
		Runnable task = () -> {
			String threadName = Thread.currentThread().getName();
            int cnt = 0; for (int i = 0; i < 100000000; i++) {cnt+=i;}
            System.out.println(cnt + " Greetings from " + threadName);
		};

		task.run();

		Thread thread = new Thread(task);
		thread.start();
		System.out.println("CreateNewThread::main ends here.");
	}
}


