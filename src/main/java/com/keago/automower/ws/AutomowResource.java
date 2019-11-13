package com.keago.automower.ws;

import com.keago.automower.Automow;

import java.util.ArrayList;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/libon")
public class AutomowResource {

    @POST
    @Path("mow")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response processMowers(@NotNull(message = "Request body may not be null") @Valid AutomowBean mow)
            throws AutomowException {

        final String result = mowLawn(mow);
        final ArrayList<ResponseBean> jsonRepr = new ArrayList<ResponseBean>();
        for (String position : result.split(System.getProperty("line.separator"))) {
            jsonRepr.add(new ResponseBean(position));
        }

        return Response.status(Response.Status.OK).entity(jsonRepr).build();
    }

    public String mowLawn(AutomowBean mow) throws AutomowException {
        Automow automow = new Automow(mow.getLawnArea());
        for (MowerBean mowerBean : mow.getMowers()) {
            automow.addMower(mowerBean.getPosition(), mowerBean.getActions());
        }
        return automow.mow();
    }
}