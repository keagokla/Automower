package com.keago.automower;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LawnTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void isValidWidth() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(Lawn.ILLEGAL_WIDTH);
        new Lawn(-5, 10, null);
    }

    @Test
    public void isValidHeigth() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(Lawn.ILLEGAL_WIDTH);
        new Lawn(15, -25, null);
    }
}
