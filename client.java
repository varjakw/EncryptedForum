import java.net.*;
import java.io.*;

public class client{

	//init socket and streams
	private Socket socket = NULL;
	private DataInputStream  dis = NULL;
	private DataOutputStream dos = NULL;

	public Client(String address, int port){
		//create connection
		try{
			socket = new Socket(address,port)
			System.out.println("Connection established")

			//input from user
			dis = new DataInputStream(System.in);
			//send output to socket
			dos = new DataOutputStream(socket.getOutputStream());		
		}catch(UnknownHostException x){
			System.out.println("Unknown Host Exception")
		}catch(IOException y){
			System.out.println("IO Exception")
		}

		//read from input until "Over"
		String line = "";

		while(!line.equals("Over")){
			try{
				line = dis.readLine();
				out.writeUTF(line);
			}catch(IOException y){
			System.out.println("IO Exception");
			}
		}

		//close conection
		try{
			dis.close();
			out.close();
			socket.close();
		}catch(IOException y){
			System.out.print("IO Exception");
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in); 
    	System.out.println("Enter your port");

    	int port = scanner.nextInt();

		//create client
		Client client = new Client("127.0.0.1", port)
	}

}
