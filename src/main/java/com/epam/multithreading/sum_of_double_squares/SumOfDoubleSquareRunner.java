package com.epam.multithreading.sum_of_double_squares;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

public class SumOfDoubleSquareRunner {
//    public static final int LIMIT = 500_000_000;
    public static final int LIMIT = 500;

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        Double[] array = generateRandomDoubles();
        System.out.println(sumOfSquares(new ForkJoinPool(), array));
        System.out.println(System.currentTimeMillis() - time + " ms");
        long currentTimeMillis = System.currentTimeMillis();
        double sum = 0;
        for (double v : array) {
            sum += v * v;
        }
        System.out.println(sum);
        System.out.println(System.currentTimeMillis() - currentTimeMillis + " ms");
    }

    private static double sumOfSquares(ForkJoinPool pool, Double[] array) {
        int n = array.length;
        Applyer a = new Applyer(array, 0, n, null);
        pool.invoke(a);
        return a.result;
    }

    private static Double[] generateRandomDoubles() {
        Random random = new Random();
        return Stream
                .generate(random::nextDouble)
                .limit(LIMIT)
                .toArray(Double[]::new);
    }
}
