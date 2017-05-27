package pl.com.labaj.jaspis.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import pl.com.labaj.jaspis.connector.GPIOConnector;
import pl.com.labaj.jaspis.connector.PinConfig;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/connector")
@Singleton
public class ConnectorPoint {

//        @Resource
    @Inject
//    @Autowired
            GPIOConnector controller;


    @POST
    @Path("configuration")
    @Consumes(APPLICATION_JSON)
    @Produces(TEXT_PLAIN)
    public String configure(List<PinConfig> configList) {
        controller.configure(configList);
        return controller.printConfiguration();
    }
}
