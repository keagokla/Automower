package com.keago.automower;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CommandParserTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    @Test
    public void nominalCaseOneMower() {
        final ArrayList<String> commands = new ArrayList<String>();
        commands.add("5   5");
        commands.add("1   2 N");
        commands.add("GAGAGAGAA");
        final CommandParser cp = new CommandParser();
        
        final Lawn newLawn = cp.buildLawn(commands);
        Assert.assertNotNull(newLawn);
        Assert.assertTrue(newLawn instanceof Lawn);
    }
    
    @Test
    public void nominalCaseTwoMowers() {
        final ArrayList<String> commands = new ArrayList<String>();
        commands.add("5 5");
        commands.add("1 2 N");
        commands.add("GAGAGAGAA");
        commands.add("3 3 E");
        commands.add("AADAADADDA");
        final CommandParser cp = new CommandParser();
        
        final Lawn newLawn = cp.buildLawn(commands);
        Assert.assertNotNull(newLawn);
        Assert.assertTrue(newLawn instanceof Lawn);
    }
    
    @Test
    public void errorCaseMissingLawn() {
        final ArrayList<String> commands = new ArrayList<String>();
        commands.add("1 2 N");
        commands.add("GAGAGAGAA");
        final CommandParser cp = new CommandParser();
        
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(CommandParser.ILLEGAL_COMMANDS);
        cp.buildLawn(commands);
    }
    
    @Test
    public void errorCaseMissingMower() {
        final ArrayList<String> commands = new ArrayList<String>();
        commands.add("5 5");
        final CommandParser cp = new CommandParser();
        
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(CommandParser.ILLEGAL_COMMANDS);
        cp.buildLawn(commands);
    }
    
    @Test
    public void errorCaseMissingMowerCommands() {
        final ArrayList<String> commands = new ArrayList<String>();
        commands.add("5 5");
        commands.add("1 2 N");
        final CommandParser cp = new CommandParser();
        
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(CommandParser.ILLEGAL_COMMANDS);
        cp.buildLawn(commands);
    }
    
    @Test
    public void errorCaseInvalidLawnDimension() {
        final ArrayList<String> commands = new ArrayList<String>();
        commands.add("-5 15");
        commands.add("1 2 N");
        commands.add("GAGAGAGAA");
        final CommandParser cp = new CommandParser();
        
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(CommandParser.ILLEGAL_DIMENSION);
        cp.buildLawn(commands);
    }
    
    @Test
    public void errorCaseInvalidMowerPosition() {
        final ArrayList<String> commands = new ArrayList<String>();
        commands.add("5 15");
        commands.add("1 -2 N");
        commands.add("GAGAGAGAA");
        final CommandParser cp = new CommandParser();
        
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(CommandParser.ILLEGAL_POSITION);
        cp.buildLawn(commands);
    }
    
    @Test
    public void errorCaseInvalidMowerCommands() {
        final ArrayList<String> commands = new ArrayList<String>();
        commands.add("5 15");
        commands.add("1 2 N");
        commands.add("GADBGAGAA");
        final CommandParser cp = new CommandParser();
        
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(CommandParser.ILLEGAL_MOWER_COMMANDS);
        cp.buildLawn(commands);
    }
    
    @Test
    public void errorCaseMowerOutBounds() {
        final ArrayList<String> commands = new ArrayList<String>();
        commands.add("5 15");
        commands.add("6 22 N");
        commands.add("AADAADADDA");
        final CommandParser cp = new CommandParser();
        
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(CommandParser.ILLEGAL_OUT_OF_BOUNDS_MOWER);
        cp.buildLawn(commands);
    }

}
