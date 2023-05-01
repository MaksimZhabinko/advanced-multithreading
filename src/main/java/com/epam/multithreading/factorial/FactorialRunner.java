package com.epam.multithreading.factorial;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class FactorialRunner {
    public static void main(String[] args) {
        final int numberOfProcessors = Runtime.getRuntime().availableProcessors();
        final ForkJoinPool forkJoinPool = new ForkJoinPool(numberOfProcessors);

        ForkJoinTask<Integer> forkJoinTask = new Factorial(5);
        Integer result = forkJoinPool.invoke(forkJoinTask);

        System.out.println("The result is : " + result);
    }
}
