package com.epam.multithreading.blurring;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkBlurRunner {
    public static void main(String[] args) {
        int[] src = new int[] {1, 2, 3, 4, 5, 6, 7};
        int[] dst = new int[] {9, 0, 5, 1, 5, 9, 2};

        final int numberOfProcessors = Runtime.getRuntime().availableProcessors();
        final ForkJoinPool forkJoinPool = new ForkJoinPool(numberOfProcessors);

        ForkJoinTask forkJoinTask = new ForkBlur(src, 0, src.length, dst);
        forkJoinPool.invoke(forkJoinTask);
    }
}
