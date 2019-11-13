package com.keago.automower.ws;

public class ResponseBean {

    private String position;

    public ResponseBean(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
