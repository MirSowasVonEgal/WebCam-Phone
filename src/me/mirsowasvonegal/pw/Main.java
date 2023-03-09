package me.mirsowasvonegal.pw;

import com.sun.net.httpserver.HttpServer;
import me.mirsowasvonegal.pw.web.HHandler;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {

    public static HttpServer httpServer;

    public static void main(String[] args) throws IOException {
        createHttpServer(8080);
        createWSServer(8081);
    }

    public static void createWSServer(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081);
        System.out.println("WebSocket server started on port 8081");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Received message from client: " + inputLine);

                    // Echo the message back to the client
                    out.println(inputLine);
                }

                System.out.println("Client disconnected: " + clientSocket.getInetAddress().getHostAddress());
                in.close();
                out.close();
            } catch (IOException e) {
                System.err.println("Error handling client: " + e.getMessage());
            } finally {
                clientSocket.close();
            }
        }
    }

    public static boolean createHttpServer(int port) {
        try {
            httpServer = HttpServer.create(new InetSocketAddress(port),0);
            httpServer.createContext("/", new HHandler());
            httpServer.start();
            return true;
        } catch (IOException e) {
            return false;
        }
    }



}
