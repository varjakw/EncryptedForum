import java.io.*;
import java.net.*;
import java.util.Scanner;

// Client class
public class client
{
    public static void main(String[] args) throws IOException
    {
        try
        {
            Scanner scanner = new Scanner(System.in);
            InetAddress ip = InetAddress.getByName("localhost");

            // connect to server port 5050
            Socket socket = new Socket(ip, 5050);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            while (true)
            {
                System.out.println(dis.readUTF());
                String message = scanner.nextLine();
                dos.writeUTF(message);

                if(message.equals("2"))
                {
                    socket.close();
                    System.out.println("Connection closed");
                    break;
                }

                // print message recieved
                String received = dis.readUTF();
                System.out.println(received);
            }

            // closing resources
            scanner.close();
            dis.close();
            dos.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}