package pl.com.labaj.jaspis.server;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfiguration {
    @Bean
    public EmbeddedServer getEmbeddedServer() {
        return new EmbeddedServer();
    }

    static Class<?>[] getPoints() {
        return new Class[]{ConnectorPoint.class, DigitalOutputPoint.class};
    }
}
