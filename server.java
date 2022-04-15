import java.net.*;
import java.io.*;
 
public class Server
{
    //initialize socket and streans
    private Socket          socket   = NULL;
    private ServerSocket    serverSocket   = NULL;
    private DataInputStream din       =  NULL;
 
    // constructor with port
    public Server(int port)
    {
        // starts server and waits for connection
        try
        {   
            //serversocket waits for client to make new socket on this port (same as client port)
            server = new ServerSocket(port);
            System.out.println("Server created");
 
            System.out.println("Waiting for connection");
 
            //socket for communication with client
            socket = server.accept();
            System.out.println("Client connected");
 
            // takes input from the client socket
            in = new DataInputStream(
                new BufferedInputStream(socket.getInputStream()));
 
            String line = "";
 
            // reads message from client until "Over" 
            while (!line.equals("Over"))
            {
                try{
                    line = in.readUTF();
                    System.out.println(line);
                }
                catch(IOException i)
                {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");
 
            // close connection
            socket.close();
            in.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
 
    public static void main(String args[])
    {
        Server server = new Server(5000);
    }
}