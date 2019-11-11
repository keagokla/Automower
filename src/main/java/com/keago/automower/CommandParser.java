package com.keago.automower;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse the commands related to mowers.
 */
public class CommandParser {
    public static final String ILLEGAL_DIMENSION =
            "Expected integer values separated by a space for the width and length. Example: 5 5";
    public static final String ILLEGAL_COMMANDS = "Expected lawn's dimension and mower commands";
    public static final String ILLEGAL_POSITION =
            "Expected integer values separated by a space for coordinates, and then the orientation. Example: 1 2 N";
    public static final String ILLEGAL_MOWER_COMMANDS =
            "Expected a list of characters between G, D, A. Example: GAGAGAGAA ";
    public static final String ILLEGAL_OUT_OF_BOUNDS_MOWER =
            "The initial position of the mower is out of the lawn's bounds";
    public static final String INVALID_INT_VALUE = "Unable to parse the value. Expecting an integer value";

    /**
     * Parse the commands in order to build the appropriate lawn and mowers.
     * 
     * @param commands the commands related to mowers
     * @return A lawn with its mowers ready to work
     * @throws IllegalArgumentException if the commands can not be parsed
     */
    public Lawn buildLawn(List<String> commands) throws IllegalArgumentException {
        if (commands.size() < 3 || commands.size() % 2 == 0) {
            throw new IllegalArgumentException(ILLEGAL_COMMANDS);
        }

        String[] lawnDimension = commands.get(0).split(" ");
        if (lawnDimension.length > 2) {
            throw new IllegalArgumentException(ILLEGAL_DIMENSION);
        }

        int lawnWidth = -1;
        int lawnLength = -1;
        try {
            lawnWidth = Integer.parseInt(lawnDimension[0]);
            lawnLength = Integer.parseInt(lawnDimension[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ILLEGAL_DIMENSION);
        }
        if (lawnWidth < 0 || lawnLength < 0) {
            throw new IllegalArgumentException(ILLEGAL_DIMENSION);
        }

        ArrayList<Mower> mowers = new ArrayList<Mower>();
        for (int i = 1; i < commands.size(); i += 2) {
            Mower newMower = buildMower(commands.get(i), commands.get(i + 1));
            if (isOutOfLawBounds(lawnWidth, lawnLength, newMower.getPosition())) {
                throw new IllegalArgumentException(ILLEGAL_OUT_OF_BOUNDS_MOWER);
            }
            mowers.add(newMower);
        }

        return new Lawn(lawnWidth, lawnLength, mowers);
    }

    /**
     * Check if the mower's initial position is out of the lawn's bounds.
     * @param lawnWidth  The width of the lawn
     * @param lawnLength The length of the lawn
     * @param position   the position that must be checked
     * @return <code>True</code> if the location is out of the lawn's
     *         bounds.<code>False otherwise</code>
     */
    private boolean isOutOfLawBounds(int lawnWidth, int lawnLength, Point position) {
        return (position.x < 0 || position.x > lawnWidth || position.y < 0 && position.y > lawnLength);
    }

    /**
     * Parse the commands in order to build the appropriate mowers.
     * 
     * @param mowerPosition mower's initial position
     * @param mowerCommands mower's commands
     * @return a mower with its initial position and the commands to execute
     */
    private Mower buildMower(String mowerPosition, String mowerCommands) {
        String[] mowerInitialPosition = mowerPosition.split(" ");
        if (mowerInitialPosition.length > 3) {
            throw new IllegalArgumentException(ILLEGAL_POSITION);
        }
        if (mowerCommands.isEmpty()) {
            throw new IllegalArgumentException(ILLEGAL_MOWER_COMMANDS);
        }

        int abs = getIntegerValue(mowerInitialPosition[0]);
        int ord = getIntegerValue(mowerInitialPosition[1]);
        if (abs < 0 || ord < 0) {
            throw new IllegalArgumentException(ILLEGAL_POSITION);
        }
        Point position = new Point(abs, ord);

        MowerOrientation orientation = MowerOrientation.valueOfLabel(mowerInitialPosition[2]);
        if (null == orientation) {
            throw new IllegalArgumentException(ILLEGAL_POSITION);
        }

        ArrayList<MowerDirection> commands = new ArrayList<MowerDirection>();
        for (String ch : mowerCommands.split("")) {
            MowerDirection direction = MowerDirection.valueOfLabel(ch);
            if (null != direction) {
                commands.add(direction);
            } else {
                throw new IllegalArgumentException(ILLEGAL_MOWER_COMMANDS);
            }
        }

        return new Mower(position, orientation, commands);
    }

    private int getIntegerValue(String str) {
        int value = -1;
        try {
            value = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_INT_VALUE);
        }
        return value;
    }
}
