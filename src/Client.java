import java.io.*;
import  java.net.*;
public class Client{
    protected BufferedReader reader=null;
    protected PrintWriter printWriter=null;
    private Socket socket=null;


    public void establish(String address,int port){
        try {
            socket=new Socket(address,port);
            System.out.println("Connected!!");
            reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter=new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void close(){
        try {
            socket.close();
            reader.close();
            printWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}