package com.keago.automower;

import java.util.ArrayList;

/**
 * Area that needs to be mowed.
 */
public final class Lawn {
    public static final String ILLEGAL_WIDTH = "You must specify a positive value for the lawn's width";
    public static final String ILLEGAL_LENGTH = "You must specify a positive value for the lawn's width";
    private final int width;
    private final int length;
    private final ArrayList<Mower> mowers;

    /**
     * Create a new Lawn with its dimension and its mowers.
     * 
     * @param width Lawn's width
     * @param length Lawn's length
     * @param mowers Lawn's mowers
     */
    public Lawn(int width, int length, ArrayList<Mower> mowers) {
        if (width < 0) {
            throw new IllegalArgumentException(ILLEGAL_WIDTH);
        }
        if (length < 0) {
            throw new IllegalArgumentException(ILLEGAL_LENGTH);
        }

        this.width = width;
        this.length = length;
        this.mowers = mowers;
    }

    public ArrayList<Mower> getMowers() {
        return mowers;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }
}
