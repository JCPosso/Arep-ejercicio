package org.example;
import java.io.IOException;

/**
 * @author JCPosso
 * @version 1
 */
public class HelloWorldServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = new HttpServer();
        server.start();
    }
}
