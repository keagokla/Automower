package com.keago.automower.ws;

import java.util.ArrayList;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Area that needs to be mowed.
 */
public final class AutomowBean {
    
    @NotNull(message = "Lawn's dimensions are mandatory")
    @Pattern(regexp = "^\\s*(\\d+)\\s+(\\d+)\\s*$", message = "Expected 2 integer values separated by a space")
    private String lawnArea;
    
    @Valid
    private ArrayList<MowerBean> mowers;
    
    public String getLawnArea() {
        return lawnArea;
    }
    
    public void setLawnArea(String lawnArea) {
        this.lawnArea = lawnArea;
    }

    public ArrayList<MowerBean> getMowers() {
        return mowers;
    }

    public void setMowers(ArrayList<MowerBean> mowers) {
        this.mowers = mowers;
    }    
}
