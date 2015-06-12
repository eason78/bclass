import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerThread extends Thread {
	
	private static final int Port = 10000;  
    ServerSocket serversocket = null;   
    private CommentList list = null;
    
   
    public ServerThread(CommentList newlist) {
		list = newlist;
		start();
	}
    

    
    public void run() {  
  
        try {   
            serversocket = new ServerSocket(Port);
            System.out.println("开始");
            while (true) {   
                Socket socket = serversocket.accept();  
                BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));   
                String msg = buffer.readLine();  

                CommentList.addOnScreenComments(msg);
                list.addNew_number();
                System.out.println(msg);
                System.out.println("没消息");
            }  
  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                serversocket.close();
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
}
