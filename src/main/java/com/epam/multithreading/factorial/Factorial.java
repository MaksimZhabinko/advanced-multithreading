package com.epam.multithreading.factorial;

import java.util.concurrent.RecursiveTask;

public class Factorial extends RecursiveTask<Integer> {
    private final Integer n;

    public Factorial(Integer n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1) {
            return 1;
        } else {
            Factorial factorial = new Factorial(n-1);
            factorial.fork();
            return n * factorial.join();
        }
    }
}
