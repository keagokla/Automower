package com.keago.automower;

/**
 * Mower's cardinal orientation on the lawn.
 */
public enum MowerOrientation {
    NORTH("N", 0), EAST("E", 90), SOUTH("S", 180), WEST("W", 270);

    private String label;
    private int angle;

    private MowerOrientation(String orientation, int angle) {
        this.label = orientation;
        this.setAngle(angle);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public static MowerOrientation valueOfLabel(String label) {
        for (MowerOrientation orientation : values()) {
            if (orientation.label.equals(label)) {
                return orientation;
            }
        }
        return null;
    }

    public static MowerOrientation valueOfAngle(int angle) {
        for (MowerOrientation orientation : values()) {
            if (orientation.angle == angle) {
                return orientation;
            }
        }
        return null;
    }
}
