package com.keago.automower;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Automower launcher.
 * Get a list of commands and then call the mower
 *
 */
public class Launcher {

    
    public static void main(String[] args) {
        ArrayList<String> commands = new ArrayList<String>(getMowerCommands(args));
        final CommandParser cp = new CommandParser();
        final Lawn mowerLawn = cp.buildLawn(commands);
        
        for (Mower mower : mowerLawn.getMowers()) {
            mower.mow(mowerLawn.getWidth(), mowerLawn.getLength());
        }
    }

    private static List<String> getMowerCommands(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expected a file with the mower commands");
        }
        
        List<String> result = null;
        try (Stream<String> lines = Files.lines(Paths.get(args[0]))) {
            result = lines.collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Failed to read file");
            e.printStackTrace();
        }
        
        return result;
    }

}
