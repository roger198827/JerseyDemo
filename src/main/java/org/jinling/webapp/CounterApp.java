package org.jinling.webapp;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;


@Path("/counters")
public class CounterApp {
    // static map of all counters
    private static final HashMap<String, Integer> allCounters = new HashMap<String, Integer>();

    //get a list of all counters and their current value
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map GetCounters() {
        return allCounters;
    }

    // get the current value of a given counter
    @GET
    @Path("/{param}")
    @Produces("text/plain")
    public Response getSpecificCounter(@PathParam("param") String namedCounter) {

        if (allCounters.containsKey(namedCounter)){
            Response.ResponseBuilder response = Response.ok(String.valueOf(allCounters.get(namedCounter)));
            return response.build();
        }
        else{
            Response.ResponseBuilder response = Response.accepted("The counter doesn't exist");
            return response.build();
        }

    }

    // increment a named counter by 1, if the named counter doesn't exist, create one
    @POST
    @Path("/{param}")
    public Response updateCounter(@PathParam("param") String namedCounter) {

        try{
            int count = allCounters.containsKey(namedCounter) ? allCounters.get(namedCounter) : 0;
            allCounters.put(namedCounter, count + 1);
            Response.ResponseBuilder response = Response.ok();
            return response.build();
        }catch (Exception e) {
            Response.ResponseBuilder response = Response.serverError();
            return response.build();
        }

    }

    // delete a specific counter
    @DELETE
    @Path("/{param}")
    public Response deleteSpecificCounter(@PathParam("param") String namedCounter) {
        if (allCounters.containsKey(namedCounter)){
            try{
                allCounters.remove(namedCounter);
            }catch (Exception e) {
                Response.ResponseBuilder response = Response.serverError();
                return response.build();
            }
            Response.ResponseBuilder response = Response.ok();
            return response.build();
        }
        else{
            Response.ResponseBuilder response = Response.accepted("The counter doesn't exist");
            return response.build();
        }
    }

    // delete all counters
    @DELETE
    public Response deleteCounters() {
        try {
            allCounters.clear();
            Response.ResponseBuilder response = Response.ok();
            return response.build();
        }catch (Exception e) {
            Response.ResponseBuilder response = Response.serverError();
            return response.build();
        }

    }

}
