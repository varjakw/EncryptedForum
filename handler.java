import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class handler extends Thread
{
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket socket;
    List<String> previousPosts = new ArrayList<String>();


    // Constructor
    public handler(Socket socket, DataInputStream dis, DataOutputStream dos)
    {
        this.socket = socket;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run()
    {
        String answer;
        while (true)
        {
            try {

                // Ask user
                dos.writeUTF("Type a post or type '1' to see previous posts or '2' to close the connection."+
                        "Type exit to close connection.");
                answer = dis.readUTF();

                // write on output stream
                switch (answer) {
                    case "1" :
                        for (int i = 0; i < previousPosts.size(); i++) {
                            dos.writeUTF(previousPosts.get(i));
                        }
                        break;
                    case "2":
                        this.socket.close();
                        System.out.println("Connection closed");
                        break;
                    default:
                        previousPosts.add(answer);
                        dos.writeUTF("Message posted");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}