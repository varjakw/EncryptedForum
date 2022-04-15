class handler extends Thread 
{
    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss");
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
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
                        dos.writeUTF(previousPosts);
                        for (int i = 0; i < answer.size(); i++) {              
                        dos.writeUTF(answer.get(i));         
                        } 
                        break;
                    case "2":
                        this.s.close();
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
          
        try
        {
            this.dis.close();
            this.dos.close();
              
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}