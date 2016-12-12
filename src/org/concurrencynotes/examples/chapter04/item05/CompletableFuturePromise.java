package org.concurrencynotes.examples.chapter04.item05;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFuturePromise {
    public static void main(String[] args) throws Exception {

        final CompletableFuture<Integer> futureMain = new CompletableFuture<>();

        class Client extends Thread {
            CompletableFuture<Integer> future;

            Client(String threadName, CompletableFuture<Integer> future) {
                super(threadName);
                this.future = future;
            }

            @Override
            public void run() {
                try {
                    System.out.println(this.getName() + ": " + future.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        new Client("Client1", futureMain).start();
        new Client("Client2", futureMain).start();
        System.out.println("Waiting");
        futureMain.complete(500); // This is promise
//      futureMain.completeExceptionally(new Exception());
    }
}

