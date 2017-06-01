package pl.com.labaj.jaspis.server;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.com.labaj.jaspis.connector.PinConfig;
import pl.com.labaj.jaspis.connector.TestConnectorConfiguration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static com.pi4j.io.gpio.PinMode.DIGITAL_OUTPUT;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;
import static pl.com.labaj.jaspis.connector.PinConfig.pin;
import static pl.com.labaj.jaspis.server.ServerConfig.CONTEXT_CONFIG;

public class ConnectorPointTest extends JerseyTest {

    @Override
    protected Client getClient() {
        Client client = super.getClient();

        client.register(LoggingFeature.class);

        return client;
    }

    @Override
    protected Application configure() {

        ApplicationContext context = new AnnotationConfigApplicationContext(TestConnectorConfiguration.class);
        return new ServerConfig().property(CONTEXT_CONFIG, context);
    }

    @Test
    public void testConfiguration() {
        //given
        Entity<?> configList = Entity.json(new PinConfig[]{
                pin(1, DIGITAL_OUTPUT),
                pin(2, DIGITAL_OUTPUT)});
        //when
        Response response = target("/connector").path("configuration").request(TEXT_PLAIN).post(configList);
        //then
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        assertThat(response.readEntity(String.class)).isEqualTo("[1 : " + PinConfig.NONE + ", 2 : " + DIGITAL_OUTPUT.toString() + "]");
    }
}
