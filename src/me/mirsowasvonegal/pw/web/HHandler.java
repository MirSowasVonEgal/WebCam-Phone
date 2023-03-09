package me.mirsowasvonegal.pw.web;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

public class HHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Headers h = exchange.getResponseHeaders();



        String line;
        StringBuilder resp = new StringBuilder();

        try {

            File newFile = new File("src/me/mirsowasvonegal/pw/web/index.html");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(newFile), "UTF-8"));

            while ((line = bufferedReader.readLine()) != null) {
                resp.append(line).append("\n");
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        h.add("Content-Type", "text/html");
        exchange.sendResponseHeaders(200, resp.length());
        OutputStream os = exchange.getResponseBody();
        os.write(resp.toString().getBytes());
        os.close();
    }
}
