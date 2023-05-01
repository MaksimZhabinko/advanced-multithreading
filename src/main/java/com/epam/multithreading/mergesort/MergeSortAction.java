package com.epam.multithreading.mergesort;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int array[];

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length < 2) {
            return;
        }
        int middle = array.length / 2;

        int[] left = new int[middle];
        System.arraycopy(array, 0, left, 0, middle);

        int[] right = new int[array.length - middle];
        System.arraycopy(array, middle, right, 0, array.length - middle);

        invokeAll(new MergeSortAction(left), new MergeSortAction(right));
        merge(left, right);
    }

    private void merge(int[] left, int[] right) {
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }
        while (i < left.length) {
            array[k++] = left[i++];
        }
        while (j < right.length) {
            array[k++] = right[j++];
        }
    }
}
