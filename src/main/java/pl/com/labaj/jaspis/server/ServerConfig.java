package pl.com.labaj.jaspis.server;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static java.util.logging.Level.FINE;
import static org.glassfish.jersey.logging.LoggingFeature.DEFAULT_MAX_ENTITY_SIZE;
import static org.glassfish.jersey.logging.LoggingFeature.Verbosity.PAYLOAD_TEXT;

public class ServerConfig extends ResourceConfig {

    static {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        Logger.getLogger("").setLevel(Level.FINEST);
    }

    public static final String CONTEXT_CONFIG = "contextConfig";

    public ServerConfig() {
        Logger logger = Logger.getLogger(Server.class.getName());
        register(new LoggingFeature(logger, FINE, PAYLOAD_TEXT, DEFAULT_MAX_ENTITY_SIZE));

        registerClasses(RequestContextFilter.class, JacksonFeature.class);

        registerClasses(ConnectorPoint.class, DigitalOutputPoint.class);
    }
}
