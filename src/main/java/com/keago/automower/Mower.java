package com.keago.automower;

import java.awt.Point;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * An automower driven by short commands.
 *
 */
public class Mower {
    private Point position;
    private MowerOrientation orientation;
    private final ArrayList<MowerDirection> commands;
    
    private static final Logger LOGGER = LogManager.getLogger(Mower.class);


    /**
     * Create a new mower with its position, its orientation, and the commands to apply.
     * 
     * @param position mower's position
     * @param orientation mower's orientation
     * @param commands the commands that the mower must apply
     */
    public Mower(Point position, MowerOrientation orientation, ArrayList<MowerDirection> commands) {
        this.position = position;
        this.orientation = orientation;
        this.commands = commands;
    }
    
    /**
     * Apply the commands in order to mow the lawn.
     * 
     * @param width lawn's width
     * @param length lawn's length
     */
    public void mow(int width, int length) {
        for (MowerDirection direction : commands) {
            process(width, length, direction);
        }
        LOGGER.info(this.position.x + " " + this.position.y + " " + this.orientation.getLabel());
    }

    /**
     * Process the mower's commands.
     * 
     * @param width lawn's width
     * @param length lawn's length
     * @param direction the direction tha tmust follow the mower
     */
    public void process(int width, int length, MowerDirection direction) {
        switch (direction) {
            case RIGHT:
                rotateRight();
                break;
            case LEFT:
                rotateLeft();
                break;
            case ADVANCE:
                move(width, length);
                break;
            default:
                break;
        }
    }

    private void rotateLeft() {
        int newAngle = this.orientation.getAngle() - 90;
        this.orientation = (newAngle == -90) ? MowerOrientation.WEST : MowerOrientation.valueOfAngle(newAngle);
    }

    private void rotateRight() {
        int newAngle = this.orientation.getAngle() + 90;
        this.orientation = (newAngle == 360) ? MowerOrientation.NORTH : MowerOrientation.valueOfAngle(newAngle);
    }

    private void move(int width, int length) {
        switch (this.orientation) {
            case NORTH:
                if (this.position.y < length) {
                    this.position.y++;
                }
                break;
            case EAST:
                if (this.position.x < width) {
                    this.position.x++;
                }
                break;
            case SOUTH:
                if (this.position.y > 0) {
                    this.position.y--;
                }
                break;
            case WEST:
                if (this.position.x > 0) {
                    this.position.x--;
                }
                break;
            default:
                break;
        }
    }

    public Point getPosition() {
        return position;
    }
}
