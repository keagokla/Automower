package com.keago.automower.ws;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class AutomowException extends Exception implements ExceptionMapper<AutomowException> {

    private static final long serialVersionUID = 1L;
    public static final String ILLEGAL_STATE = "Unable to build the automower's environment, due to a wrong parameter!";

    public AutomowException() {
        super(ILLEGAL_STATE);
    }

    public AutomowException(String string) {
        super(string);
    }

    @Override
    public Response toResponse(AutomowException exception) {
        return Response.status(400).entity(exception.getMessage()).type("text/plain").build();
    }

}
