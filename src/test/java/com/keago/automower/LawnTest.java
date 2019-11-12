package com.keago.automower;

import com.keago.automower.ws.AutomowException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class LawnTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test(expected = AutomowException.class)
    public void isValidWidth() throws AutomowException {
        new Lawn(-5, 10);
    }

    @Test(expected = AutomowException.class)
    public void isValidHeigth() throws AutomowException {
        new Lawn(15, -25);
    }
}
