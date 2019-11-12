package com.keago.automower.ws;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Resource describing an automower driven by short commands.
 *
 */
public class MowerBean {
    public static final String ILLEGAL_POSITION =
            "Expected positive integer values separated by a space, and then the orientation. Example: 1 2 N";

    @NotNull(message = "Mower's initial position is mandatory")
    @Pattern(regexp = "^\\s*(\\d+)\\s+(\\d+)\\s+([NEWS])\\s*$", message = ILLEGAL_POSITION)
    private String position;
    
    @NotNull(message = "Mower's movements are mandatory")
    @Pattern(regexp = "^[AGD]+$", message = "Expected a list of characters between G, D, A. Example: GAGAGAGAA")
    private String actions;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }


}
