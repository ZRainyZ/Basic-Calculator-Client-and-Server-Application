package sample;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private String output;


    public void StartConnect() {
        try {
            socket = new Socket(InetAddress.getLocalHost(), Server.SERVER_PORT);
            System.out.println("Connecting to port: "+ Server.SERVER_PORT);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

    }

    public String sendInput(String input){
        StartConnect();
        try {
            dataOutputStream.writeUTF(input);
            System.out.println("Client Sent: "+input);
            dataOutputStream.flush();
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
        try {
            output = dataInputStream.readUTF();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        StopConnect();
        return output;
    }

    public void StopConnect(){
        try {
            socket.close();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

}
