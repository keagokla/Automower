package com.keago.automower;

/**
 * Mower direction. The mower can advance or rotate according to the direction.
 */
public enum MowerDirection {
    RIGHT("D"), LEFT("G"), ADVANCE("A");

    private String label;

    private MowerDirection(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public static MowerDirection valueOfLabel(String label) {
        for (MowerDirection direction : values()) {
            if (direction.label.equals(label)) {
                return direction;
            }
        }
        return null;
    }
}
