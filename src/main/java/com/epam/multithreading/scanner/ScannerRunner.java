package com.epam.multithreading.scanner;

import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ScannerRunner {
    public static void main(String[] args) {
        System.out.println("Write a path ");
//        "C:\\Users\\UX503948\\IdeaProjects\\forAdvanceMultithreading"
        Scanner scanner = new Scanner(System.in);
        String path = scanner.next();

        final int numberOfProcessors = Runtime.getRuntime().availableProcessors();
        final ForkJoinPool forkJoinPool = new ForkJoinPool(numberOfProcessors);

        ForkJoinTask<ConcurrentHashMap<String, Long>> forkJoinTask = new ScannerAction(path);
        ConcurrentHashMap<String, Long> invoke = forkJoinPool.invoke(forkJoinTask);
        invoke.entrySet().stream().forEach(System.out::println);

    }
}
