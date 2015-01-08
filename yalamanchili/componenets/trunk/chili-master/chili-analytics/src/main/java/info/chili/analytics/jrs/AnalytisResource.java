/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.analytics.jrs;

import info.chili.analytics.model.Event.EventsTable;
import info.chili.analytics.service.EventsService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author ayalamanchili
 */
@Path("secured/chili/analytics")
@Component
@Scope("request")
@Produces("application/json")
@Consumes("application/json")
public class AnalytisResource {

    @Autowired
    protected EventsService eventsService;

    @Path("/events/{start}/{limit}")
    @GET
    public EventsTable events(@PathParam("start") int start, @PathParam("limit") int limit) {
        return eventsService.getEvents(start, limit);
    }
}
