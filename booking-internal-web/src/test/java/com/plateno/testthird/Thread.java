/*package com.plateno.testthird;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Thread {

	public static void main(String[] args) {

		ExecutorService executor = Executors.newCachedThreadPool();
		Task task = new Task();
		Future<Integer> result = executor.submit(task);
		executor.shutdown();
	}
}

class Task implements Callable<Integer> {
	public Integer call() throws Exception {
		System.out.println("子线程在进行计算");
		return 0;
	}
}
*/