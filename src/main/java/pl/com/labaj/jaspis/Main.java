package pl.com.labaj.jaspis;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.com.labaj.jaspis.server.ServerConfiguration;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ServerConfiguration.class);
        context.start();
    }
}
