package com.example;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class App {

    public static void main(String[] args) throws IOException {

        int port = 8085; 

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("Server started at http://localhost:" + port);
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "Hello from Java EC2 Deployment! this is to check git webhook and jenkins trigger";
            t.sendResponseHeaders(200, response.length());

            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
