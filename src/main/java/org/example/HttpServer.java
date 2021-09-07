package org.example;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.util.HashMap;

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
            clientSocket.close();

        }
        serverSocket.close();
    }
    public void serveConnection (Socket clientSocket) throws IOException, URISyntaxException {
        OutputStream out= clientSocket.getOutputStream();
        BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
        HashMap<String,String> request  = new HashMap<String,String>();
        boolean ready = false;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Received: " + inputLine);
            if(!ready){
                request.put("rq", inputLine);
                ready =true;
            } else {
                String[] line = inputLine.split( ":" );
                if (line.length>1)
                request.put( line[0], line[1] );
            }
            if (!in.ready()) {
                break;
            }
        }
        getResource(request,out);
        in.close();
    }

    public  void getResource(HashMap<String,String> response, OutputStream ClientSocket) throws IOException {
        // si es de tipo imagen llamar imagen
        // getPng( OutputStream outClientSocket)
        //si es de tipo js llamar lector archivos tanto js como html
        // computDefaultResponse()
        String outputLine = "";
        if (response.get("rq")!= null) {
            PrintWriter printWriter = new PrintWriter( ClientSocket, true );
            String[] lineRequest = response.get( "rq" ).split( " " );
            String path = lineRequest[1];
            if ( path.equals( "/" ) ) path = "/index.html";
            byte pathByte[] = findResource( path );
            if ( pathByte != null ) {
                outputLine = generateHeader( path );
                printWriter.println( outputLine );
                ClientSocket.write( pathByte );
            }
        }
        ClientSocket.close();
        //return  computDefaultResponse();
    }

    private byte[] findResource(String path) throws IOException {
        String dir = "src/main/resources/html_public" +  path;
        File resource= new File(dir);
        byte content[] = new byte[(int)resource.length()];
        if (resource.exists() && resource.isFile() ){
            FileInputStream fileStream = new FileInputStream(resource);
            fileStream.read(content);
            fileStream.close();
        }else{
            content = null;
        }
        return content;
    }

    private String generateHeader(String path) {
        String header = "";
        String[] lista = path.split("\\.");
        String type = lista[lista.length-1];
        if( type.equals( "jpg" ) || type.equals( "png" ) ){
            header += "HTTP/1.1 200 OK\r\n Content-Type: image/" + type +"\r\n" + "\r";
        }else if (type.equals( "html" ) || type.equals( "js" ) || type.equals( "css" )){
            header += "HTTP/1.1 200 OK\r\n Content-Type: text/" + type +"\r\n" + "\r\n";
        }
        return header;
    }

    public String  computDefaultResponse() throws IOException{
        File archivo = new File("src/main/resources/html_public/index.html");
        BufferedReader in = new BufferedReader( new FileReader( archivo ));
        String out = "HTTP/1.1 200 OK\r\n"+ "Content-Type: text/html \r\n"+ "\r\n";
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            out+=inputLine+"\n";
            if (!in.ready()) {break; }
        }
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
