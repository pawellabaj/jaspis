package pl.com.labaj.jaspis.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import pl.com.labaj.jaspis.connector.ConnectorConfiguration;
import pl.com.labaj.jaspis.log.Logger;
import pl.com.labaj.jaspis.log.LoggerFactory;

import static pl.com.labaj.jaspis.log.Message.msg;

public class EmbeddedServer implements InitializingBean, DisposableBean {

    private static final int SERVER_PORT = 8314;
    private static final String CONTEXT_CLASS = "contextClass";
    private static final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    private Logger logger = LoggerFactory.createLogger(getClass());

    private Server jettyServer;

    @SuppressWarnings("WeakerAccess")
    public EmbeddedServer() {
        jettyServer = new Server(SERVER_PORT);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ResourceConfig config = new ResourceConfig(JacksonFeature.class, JacksonProvider.class);
        config.registerClasses(ServerConfiguration.getPoints());

        config.property(ServerProperties.METAINF_SERVICES_LOOKUP_DISABLE, true);

        ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(config));

        ServletContextHandler context = new ServletContextHandler();

        context.setContextPath("/");
        context.addServlet(jerseyServlet, "/*");

        context.addEventListener(new ContextLoaderListener());
        context.addEventListener(new RequestContextListener());

        context.setInitParameter(CONTEXT_CLASS, AnnotationConfigWebApplicationContext.class.getName());
        context.setInitParameter(CONTEXT_CONFIG_LOCATION, ConnectorConfiguration.class.getName());

        jettyServer.setHandler(context);
    }

    @SuppressWarnings("unused")
    @EventListener()
    public void start(ContextRefreshedEvent event) {
        System.out.println("START " + event);
        try {
            jettyServer.start();
        } catch (Exception e) {
            logger.error(() -> msg("Cannot start jettyServer", e));
            System.exit(-1);
        }
        try {
            jettyServer.join();
        } catch (InterruptedException e) {
            logger.error(() -> msg("Cannot join jettyServer", e));
            System.exit(-1);
        }
    }

    @Override
    public void destroy() {
        try {
            jettyServer.stop();
        } catch (Exception e) {
            logger.error(() -> msg("Cannot stop jettyServer", e));
            System.exit(-1);
        }
    }
}
