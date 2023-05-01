package com.epam.multithreading.mergesort;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class MergeSortRunner {
    public static void main(String[] args) {
        int[] array = new Random().ints(10, 0, 50).toArray();
        System.out.println("The array in its original form: " + Arrays.toString(array));

        final int numberOfProcessors = Runtime.getRuntime().availableProcessors();
        final ForkJoinPool forkJoinPool = new ForkJoinPool(numberOfProcessors);

        ForkJoinTask mergeSortAction = new MergeSortAction(array);
        forkJoinPool.invoke(mergeSortAction);

        System.out.println("The result is: " + Arrays.toString(array));
    }
}
