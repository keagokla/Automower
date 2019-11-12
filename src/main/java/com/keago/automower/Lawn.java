package com.keago.automower;

import com.keago.automower.ws.AutomowException;


/**
 * Area that needs to be mowed.
 */
public final class Lawn {
    public static final String ILLEGAL_DIMENSION = "Expected positive values for the lawn's dimensions";
    private final int width;
    private final int length;

    /**
     * Create a new Lawn with its dimension.
     * 
     * @param width Lawn's width
     * @param length Lawn's length
     * @throws AutomowException Exception ensuring the lawn's dimensions are positive integers
     */
    public Lawn(int width, int length) throws AutomowException {
        if (width < 0 || length < 0) {
            throw new AutomowException(ILLEGAL_DIMENSION);
        }

        this.width = width;
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }
}
