package com.keago.automower.ws;

import com.keago.automower.Automow;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/libon")
public class AutomowResource {

    @POST
    @Path("mow")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String mowLawn(@NotNull(message = "Request body may not be null") @Valid AutomowBean mow)
            throws AutomowException {

        Automow automow = new Automow(mow.getLawnArea());
        for (MowerBean mower : mow.getMowers()) {
            automow.addMower(mower.getPosition(), mower.getActions());
        }

        return automow.mow();
    }
}