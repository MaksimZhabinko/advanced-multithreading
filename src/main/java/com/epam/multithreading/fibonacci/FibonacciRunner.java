package com.epam.multithreading.fibonacci;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class FibonacciRunner {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        final int numberOfProcessors = Runtime.getRuntime().availableProcessors();
        final ForkJoinPool forkJoinPool = new ForkJoinPool(numberOfProcessors);

        final ForkJoinTask<Integer> result = forkJoinPool.submit(new Fibonacci(45));

        System.out.println("The result is : " + result.join());
        System.out.println(result.get().equals(1134903170));
    }
}
