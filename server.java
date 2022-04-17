import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

public class server
{
    private static String publicKeyForum = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxwUTdXC6/8vb6Lq+4EKtsuuuZcqQ/JA1HifmUHIqvs/x5VfC+L1t/3pvkDsgUJwrSjK0htEcnJAuMzEAOqkhnu4szVOd+eF3hF8On8omoeraYYGmnxmsAK26XsMGtYeFIuvRjQAMQwNzbz5MXoqKc1WBQq3wFSUnXArE63xaHU95p5JXJ1V5bs3BysK64bCGxriuNQ97ZIAPWzYzmVOLFnfG1xO1R5t+ya93tvPgaCgbF/dq96nhOb0lVZBcAdYdAiUQ7czqaNJ7N4TFZEQaPvQL3xNU/Dv22fR2MOonY2Y3sWOm3w2cvgNJlq4lbjYMvKSkOjIboDSEfrzH1cvyAQIDAQAB";
    private static String privateKeyTrinityGroup = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDHBRN1cLr/y9vour7gQq2y665lypD8kDUeJ+ZQciq+z/HlV8L4vW3/em+QOyBQnCtKMrSG0RyckC4zMQA6qSGe7izNU5354XeEXw6fyiah6tphgaafGawArbpewwa1h4Ui69GNAAxDA3NvPkxeiopzVYFCrfAVJSdcCsTrfFodT3mnklcnVXluzcHKwrrhsIbGuK41D3tkgA9bNjOZU4sWd8bXE7VHm37Jr3e28+BoKBsX92r3qeE5vSVVkFwB1h0CJRDtzOpo0ns3hMVkRBo+9AvfE1T8O/bZ9HYw6idjZjexY6bfDZy+A0mWriVuNgy8pKQ6MhugNIR+vMfVy/IBAgMBAAECggEAMqWiDBMCI7KXZiSQCrYtSgkcOp9QQlXYjrIMtJA5PJHit0CcR3y9j3RzC7tgP+NeSOWad2yj4Vv3xeNEcak0+yMVl7Md0seECNW//P4O0snXhVTfOEHqOcn8Ub1D6dJxGS/2BfHrLOql57ts5+pNzYgG+xcUr+zilVPQRTVCIY7ybuqTp+MS+euz55JiilQYdpRCjr6eMFQS07kABly8MpjRke2TSYsTgWLspSE+53Fys48vjy3OPUpWnOA9GDqETfkZl+L4k+OSo/m7tB3Om+UnnQVFDHb2z/8YeROUVdCiaCC1dCbZlFihbie5wVpWP5+bPKwgtm325n8P2p82GQKBgQD+J284ePvsrQUc3zUC1Jw61R+5fX6N/9UZhuo3PVcg9N/Oz0DRhgLoTkw6XJeseoUHuu9rusA5QnAM5E8XknmHXRlWoX4W5/c5PlEclQYTbodSclsEW8D1HpH0Dac+gCrtgxruHaroVgXjtgdroTNLUE2GOoMSDXGuTdXtEiIrpwKBgQDIdyB4j5vYwLLpc6Nr6AQDk1cO41M1LxgEOvUUlzlAa96ydHNN/R22vVp9a9Rjr38f70C+FruV7caK7navKy9Ta09Vd43n39ZWdOndF6tGDxXSmGQq5te6rfCjWf9CBDfg9pQn51sQk8tUxB0x8M31jQl/IGNXwWH15WnH636KFwKBgDVOrCNaLOz9dV8S6hmH4qTsYtUTH2JFULsj8yhW81l0QyUrj1rjEuUK1y4+kYmZwSFX8jPUXdEkwD/T+0rkbB0aR+BFyx6e1Nnq2jR2hWm5+yQt3C1T2WsQWGiqHHQdieN7ZkCfdGN6941FdtT6YLnhRAFtcGRu8XMv3Dj3XrdtAoGBAJHj2HbFFMd/HfW2GbW7KcisXwxPtkubVWAmxBvTHnBa0LyQ8SVN02UdkxB5/Bz8KIlGJpjwlF49qGONvkQnKo8MJrVth5G7nhJXjadAxX38FoaoLyXl0BRk6B8JrTIPPddzvEj6H5UF6lFrVE/3DvzoQoQJPyMrcq5w4jYDlZjLAoGBAJsiHPuIcx/qBi0GQpBJ7F9qUrTTj+4vVqc5IVO7by6BZJ385XqJ9OQ/xQNQxa81RI4xYdfoQxlamxvHOiah4cM082Ud1xNAKq0hitI1exHX9GXaf0CuSSUsBzZSmV7gD3eWY2hLNnvuKCOnwm4REk5HV1Hbg+/c10Nx/EsEyiVc";
    private static String privateKeyUCDGroup = "test";



    //vector of connected clients
    static Vector<handler> clients = new Vector<>();
    static int i = 0; // client counter

    static Hashtable<String, PrivateKey> users = new Hashtable<String, PrivateKey>();

    //server created to listen to port 5050
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        //initialise user permissions
        //key management
        //can remove people from groups
        users.put("varjak", handler.getPrivateKey(privateKeyTrinityGroup));
        users.put("alex", handler.getPrivateKey(privateKeyTrinityGroup));
        ServerSocket serverSocket = new ServerSocket(5050);
        Socket socket;

        System.out.println("Server created ");

        // loop to catch requests
        while (true)
        {
            socket = serverSocket.accept(); //accept new request
            System.out.println("Client request received : " + socket);
            // obtain input and output streams
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            // threaded handler for new request
            handler req = new handler(socket,"client " + i, dis, dos);
            Thread t = new Thread(req);
            // add this client to active clients list
            clients.add(req);
            t.start(); //start thread
            i++; //client counter increment

        }
    }
}