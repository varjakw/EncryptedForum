import java.net.*;
import java.io.*;
 
public class Server
{
    //initialize socket and streans
    private Socket          socket   = NULL;
    private ServerSocket    serverSocket   = NULL;
    private DataInputStream dis       =  NULL;
    private DataOutputStream dos = NULL;
 
    // constructor with port
    public Server(int port)
    {
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
                    dos = new DataOutputStream(socket.getOut[utStream());

                    //give client a thread
                    Thread thread = new handler(socket, dis, dos);
                    thread.start();

                }catch(Exception e){
                    socket.close();
                    e.printStackTrace();
                }
            }  
           
    }
 
    public static void main(String args[])
    {
        Server server = new Server(5056);
    }
}