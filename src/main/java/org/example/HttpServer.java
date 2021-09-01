package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
    public  String getResource(URI resorceURL,Socket ClientSocket) throws IOException {
        // si es de tipo imagen llamar imagen
        // getPng( OutputStream outClientSocket)
        //si es de tipo js llamar lector archivos tanto js como html
        // computDefaultResponse()
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
    public void getPng( OutputStream outClientSocket){
        String route = "src/main/resources/hello-world.png";
        File file = new File(route);
        if (file.exists()) {
            try {
                BufferedImage image = ImageIO.read(file);
                ByteArrayOutputStream ArrBytes = new ByteArrayOutputStream();
                DataOutputStream writeimg = new DataOutputStream(outClientSocket);
                ImageIO.write(image, "PNG", ArrBytes);
                writeimg.writeBytes("HTTP/1.1 200 OK \r\n"
                                    + "Content-Type: image/png \r\n"
                                    + "\r\n");
                writeimg.write(ArrBytes.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("ha ocurrido error al leer imagen"+file.getName());
        }
    }
    public static void main(String[] args ) throws IOException, URISyntaxException {
        HttpServer.getInstance().start(args);
    }

}
