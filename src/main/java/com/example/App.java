package com.example;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws IOException {

        int port = 8085; // change if needed

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("Server started at http://localhost:" + port);
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "Hello from Java EC2 Deployment!";
            t.sendResponseHeaders(200, response.length());

            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
