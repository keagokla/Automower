package com.keago.automower;

import com.keago.automower.ws.AutomowException;
import java.awt.Point;
import java.util.ArrayList;

public class Automow {
    private Lawn lawn = null;
    private ArrayList<Mower> mowers = new ArrayList<Mower>();

    public Automow(String area) throws AutomowException {
        if (isNullOrEmpty(area)) {
            throw new AutomowException();
        }

        String[] lawnArea = area.split("\\s+");
        if (lawnArea.length > 2) {
            throw new AutomowException();
        }

        int lawnWidth = parseInt(lawnArea[0]);
        int lawnLength = parseInt(lawnArea[1]);
        this.lawn = new Lawn(lawnWidth, lawnLength);
    }

    public Automow addMower(String mowerPosition, String actions) throws AutomowException {
        if (isNullOrEmpty(mowerPosition) || isNullOrEmpty(actions)) {
            throw new AutomowException();
        }

        String[] mowerInitialPosition = mowerPosition.split("\\s+");
        if (actions.isEmpty() || mowerInitialPosition.length > 3) {
            throw new AutomowException();
        }

        int abs = parseInt(mowerInitialPosition[0]);
        int ord = parseInt(mowerInitialPosition[1]);
        Point position = new Point(abs, ord);
        MowerOrientation orientation = MowerOrientation.valueOfLabel(mowerInitialPosition[2]);

        if (isOutOfLawBounds(this.lawn.getWidth(), this.lawn.getLength(), position) || null == orientation) {
            throw new AutomowException();
        }

        ArrayList<MowerDirection> commands = new ArrayList<MowerDirection>();
        for (String ch : actions.split("")) {
            MowerDirection direction = MowerDirection.valueOfLabel(ch);
            if (null != direction) {
                commands.add(direction);
            } else {
                throw new AutomowException();
            }
        }

        this.mowers.add(new Mower(position, orientation, commands));
        return this;
    }

    private int parseInt(String value) throws AutomowException {
        int intValue = -1;
        try {
            intValue = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            // Throw the IllegalStateException above
        }
        if (intValue < 0) {
            throw new AutomowException();
        }
        return intValue;
    }

    private Boolean isNullOrEmpty(String str) {
        return (null == str || str.trim().isEmpty());
    }

    /**
     * Check if the mower's initial position is out of the lawn's bounds.
     * 
     * @param lawnWidth  The width of the lawn
     * @param lawnLength The length of the lawn
     * @param position   the position that must be checked
     * @return <code>True</code> if the location is out of the lawn's
     *         bounds.<code>False otherwise</code>
     */
    private boolean isOutOfLawBounds(int lawnWidth, int lawnLength, Point position) {
        return (position.x < 0 || position.x > lawnWidth || position.y < 0 && position.y > lawnLength);
    }

    public String mow() throws AutomowException {
        StringBuilder sb = new StringBuilder();
        if (mowers.isEmpty()) {
            throw new AutomowException();
        }
        for (Mower mower : mowers) {
            mower.mow(lawn.getWidth(), lawn.getLength());

            sb.append(mower.getPosition().x)
                .append(" ")
                .append(mower.getPosition().y)
                .append(" ")
                .append(mower.getOrientation().getLabel())
                .append(System.getProperty("line.separator"));
        }

        return sb.toString();
    }
    
    public ArrayList<Mower> getMowers() {
        return mowers;
    }

}
