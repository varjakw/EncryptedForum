import java.net.*;
import java.io.*;

public class server
{
    //initialize socket and streans
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private DataInputStream dis =  null;
    private DataOutputStream dos = null;

    // constructor with port
    public server(int port) throws IOException {
        //serversocket waits for client to make new socket on this port (same as client port)
        serverSocket = new ServerSocket(port);
        System.out.println("Server listening");
        //loop for client messages
        while(true){
            try{
                socket = serverSocket.accept();
                System.out.println("User connected: "+ socket);

                //get user streams
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());

                //give client a thread
                Thread thread = new handler(socket, dis, dos);
                thread.start();

            }catch(Exception e){
                socket.close();
                e.printStackTrace();
            }
        }

    }

    public static void main(String args[]) throws IOException {
        server server = new server(5050);
    }
}