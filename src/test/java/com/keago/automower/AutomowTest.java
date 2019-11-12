package com.keago.automower;

import com.keago.automower.ws.AutomowException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class AutomowTest {
    private static final String MOWER_2_RESULT = "5 1 E";
    private static final String MOWER_1_RESULT = "1 3 N";
    private static final String MOWER_2_ACTIONS = "AADAADADDA";
    private static final String MOWER_2_POSITION = "3 3 E";
    private static final String MOWER_1_ACTIONS = "GAGAGAGAA";
    private static final String MOWER_1_POSITION = "1   2 N";
    private static final String AREA = "5   5";
    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    @Test
    public void nominalCaseOneMower() throws AutomowException {
        final Automow automow = new Automow(AREA);
        automow.addMower(MOWER_1_POSITION, MOWER_1_ACTIONS);
        
        final String result = automow.mow();
        Assert.assertNotNull(result);
        Assert.assertEquals(MOWER_1_RESULT, result.trim());
    }
    
    @Test
    public void nominalCaseTwoMowers() throws AutomowException {
        final Automow automow = new Automow(AREA);
        automow.addMower(MOWER_1_POSITION, MOWER_1_ACTIONS).addMower(MOWER_2_POSITION, MOWER_2_ACTIONS);
        
        final String result = automow.mow();
        Assert.assertNotNull(result);
        final String expected = MOWER_1_RESULT + System.getProperty("line.separator") + MOWER_2_RESULT;
        Assert.assertEquals(expected, result.trim());
    }
    
    @Test(expected = AutomowException.class)
    public void errorCaseNullLawn() throws AutomowException {
        new Automow(null).mow();
    }
    
    @Test(expected = AutomowException.class)
    public void errorCaseEmptyLawn() throws AutomowException {
        new Automow("     ").mow();
    }
    
    @Test(expected = AutomowException.class)
    public void errorCaseMissingMower() throws AutomowException {
        new Automow(AREA).mow();
    }
    
    @Test(expected = AutomowException.class)
    public void errorCaseMissingMowerPosition() throws AutomowException {
        new Automow(AREA).addMower(null, MOWER_1_ACTIONS).mow();
    }
    
    @Test(expected = AutomowException.class)
    public void errorCaseMissingMowerActions() throws AutomowException {
        new Automow(AREA).addMower(MOWER_2_POSITION, "").mow();
    }
    
    @Test(expected = AutomowException.class)
    public void errorCaseInvalidArea() throws AutomowException {
        final Automow automow = new Automow("-5 15");
        automow.addMower(MOWER_1_POSITION, MOWER_1_ACTIONS).addMower(MOWER_2_POSITION, MOWER_2_ACTIONS).mow();
    }
    
    @Test(expected = AutomowException.class)
    public void errorCaseInvalidMowerPosition() throws AutomowException {
        final Automow automow = new Automow(AREA);
        automow.addMower("1 -2N", MOWER_1_ACTIONS).mow();
    }
    
    @Test(expected = AutomowException.class)
    public void errorCaseUnknownMowerCommands() throws AutomowException {
        final Automow automow = new Automow(AREA);
        automow.addMower(MOWER_1_POSITION, "GADBGAWGAA").mow();
    }
    
    @Test(expected = AutomowException.class)
    public void errorCaseMowerOutBounds() throws AutomowException {
        final Automow automow = new Automow(AREA);
        automow.addMower("6 22 N", MOWER_1_ACTIONS).mow();
    }

}
