package org.example;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class HttpServer {
    private static final HttpServer _instance = new HttpServer();

    public static  HttpServer getInstance(){
        return _instance;
    }

    private HttpServer(){}

    public void start(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println( "Listo para recibir ..." );
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println( "Accept failed." );
                System.exit( 1 );
            }
            serveConnection(clientSocket);
        }
        serverSocket.close();
    }
    public void serveConnection (Socket clientSocket) throws IOException, URISyntaxException {
        PrintWriter out= new PrintWriter( clientSocket.getOutputStream(),true );
        BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
        ArrayList<String> request  = new ArrayList<String>();

        while ((inputLine = in.readLine()) != null) {
            System.out.println("Received: " + inputLine);
            request.add(inputLine);
            if (!in.ready()) {break; }
        }
        System.out.println(request);
        String uriStr= request.get(0).split("")[1];
        URI resourceURI = new URI(uriStr);
        outputLine= getResource(resourceURI);
        out.println(outputLine);

        out.close();
        in.close();
        clientSocket.close();

    }
    public  String getResource(URI resorceURL) throws IOException {
        return  computDefaultResponse();
    }
    public String  computDefaultResponse() throws IOException{
        File archivo = new File("src/main/resources/html_public/index.html");
        BufferedReader in = new BufferedReader( new FileReader( archivo ));
        String out = "HTTP/1.1 200 OK\r\n"+ "Content-Type: text/html \r\n"+ "\r\n";
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Received: " + inputLine);
            out+=inputLine+"\n";
            if (!in.ready()) {break; }
        }
        System.out.println("Final Received: " + out);
        return out;
    }
    public static void main(String[] args ) throws IOException, URISyntaxException {
        HttpServer.getInstance().start(args);
    }

}
