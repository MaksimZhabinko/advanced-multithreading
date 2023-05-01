package com.epam.multithreading.blurring;

import java.util.concurrent.RecursiveAction;

public class ForkBlur extends RecursiveAction {
    private static final int THRESHOLD = 100000;

    private final int[] mSource;
    private final int mStart;
    private final int mLength;
    private final int[] mDestination;

    private int mBlurWidth = 15;

    public ForkBlur(int[] mSource, int mStart, int mLength, int[] mDestination) {
        this.mSource = mSource;
        this.mStart = mStart;
        this.mLength = mLength;
        this.mDestination = mDestination;
    }

    protected void computeDirectly() {
        int sidePixels = (mBlurWidth - 1) / 2;
        for (int index = mStart; index < mStart + mLength; index++) {
            float rt = 0, gt = 0, bt = 0;
            for (int mi = -sidePixels; mi <= sidePixels; mi++) {
                int mindex = Math.min(Math.max(mi + index, 0),
                        mSource.length - 1);
                int pixel = mSource[mindex];
                rt += (float)((pixel & 0x00ff0000) >> 16)
                        / mBlurWidth;
                gt += (float)((pixel & 0x0000ff00) >>  8)
                        / mBlurWidth;
                bt += (float)((pixel & 0x000000ff) >>  0)
                        / mBlurWidth;
            }

            int dpixel = (0xff000000     ) |
                    (((int)rt) << 16) |
                    (((int)gt) <<  8) |
                    (((int)bt) <<  0);
            mDestination[index] = dpixel;
        }
    }

    @Override
    protected void compute() {
        if (mLength < THRESHOLD) {
            computeDirectly();
            return;
        }

        int split = mLength / 2;

        invokeAll(new ForkBlur(mSource, mStart, split, mDestination),
                new ForkBlur(mSource, mStart + split, mLength - split,
                        mDestination));
    }
}
