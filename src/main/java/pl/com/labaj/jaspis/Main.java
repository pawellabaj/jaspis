package pl.com.labaj.jaspis;

import pl.com.labaj.jaspis.server.Server;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Server server = new Server();

        server.start();
    }
}
