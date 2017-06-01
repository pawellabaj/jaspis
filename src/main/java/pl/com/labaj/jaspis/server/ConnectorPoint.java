package pl.com.labaj.jaspis.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import pl.com.labaj.jaspis.connector.GPIOConnector;
import pl.com.labaj.jaspis.connector.PinConfig;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.ok;

@Path("/connector")
@Singleton
@Service
public class ConnectorPoint {

    @Autowired
    private GPIOConnector controller;

    @POST
    @Path("configuration")
    @Consumes(APPLICATION_JSON)
    @Produces(TEXT_PLAIN)
    public Response configure(@RequestBody PinConfig[] configList) {
        try {
            controller.configure(configList);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(INTERNAL_SERVER_ERROR).entity("See logs on the server").build();
        }
        return ok(controller.printConfiguration()).build();
    }
}
