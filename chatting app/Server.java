
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*; 

class Server{
ServerSocket server ; //declaring the Serversocket ; 
Socket socket ;  // declaring the socket ;
BufferedReader br ; // use for the Read the data from socket;
PrintWriter out ; // use for the write the data from socket;


//creating constructor........
    public Server(){
    try {
         server = new  ServerSocket(5757); //must specify the port number for contacting the Client ;
        System.out.println("Server is Ready for accept connection.");
        System.out.println("Waiting.......");
        socket = server.accept(); //server is accpet the connection ;
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));  //client se data ko recieve krne ke liye inputstream nikali from socket ;
        out = new PrintWriter(socket.getOutputStream()); //data ko client tak bhejne ke liye soket se output stream nikali ;
        // calling function for startReading() , and startWriting() ;
        startReading();
        startWriting();
         

     

        
    } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
    }
       


    }
   public void startReading(){
  //use of concept of multiThreading ;;;;
  //Thread == read karke dega ;
   Runnable r1=()->{   //lamda expression ;
    System.out.println("Reader started..");
    while(true){
try { 
  String msg = br.readLine(); //br used for reading  ;the single line ;
   if(msg.equals("quit")){
   System.out.println("Client terminated the chat");
  break; 
   } 
   System.out.println("Client :"+msg);
} catch(Exception e){
    e.printStackTrace();
}
} 

   };
   new Thread(r1).start(); //Starting the thread r1
   



    }

    public void startWriting(){
 // thread Write karke dega ; 
  Runnable r2=()->{
    System.out.println("Writer Started..");
    while(true){
 try {
    //TAking  input from the Console ;
    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
    String Content= br1.readLine(); 
    out.println(Content);
    out.flush();
 } catch (Exception e) {
    // TODO: handle exception
 }

    }
   

  };
 new  Thread(r2).start();



    }
public static void main(String[] args) {
    System.out.println("this is server Going to start..");
    new Server();
}

} 