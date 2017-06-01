package pl.com.labaj.jaspis.server;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.com.labaj.jaspis.connector.ConnectorConfiguration;
import pl.com.labaj.jaspis.log.Logger;
import pl.com.labaj.jaspis.log.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;

import static java.util.Objects.nonNull;
import static pl.com.labaj.jaspis.log.Message.msg;
import static pl.com.labaj.jaspis.server.ServerConfig.CONTEXT_CONFIG;

public class Server {

    private Logger logger = LoggerFactory.getLogger(Server.class);

    public void start() {
        ServerConfig config = new ServerConfig();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConnectorConfiguration.class);
        config.property(CONTEXT_CONFIG, context);


        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(getBaseURI(), config, false);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (nonNull(server)) {
                server.shutdownNow();
            }
        }));


        // start the server
        try {
            if (Objects.nonNull(server)) {
                server.start();
            }
            joinThread();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(() -> msg("Cannot start server", e));
        }
    }

    private void joinThread() {
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error(() -> msg("Cannot join thread", e));
        }
    }

    private URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/").port(getPort()).build();
    }

    private int getPort() {
        return 8314;
    }
}