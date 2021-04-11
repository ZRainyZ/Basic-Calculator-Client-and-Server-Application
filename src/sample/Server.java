package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static int SERVER_PORT = 5060;
    private ServerSocket serverSocket;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private String input, outputstring;
    private int outputint;
    private double outputdouble;


    public ServerSocket start()throws IOException{

        serverSocket = new ServerSocket(SERVER_PORT);
        System.out.println("Server is online");

        return  serverSocket;
    }

    public void stop() throws IOException{
        socket.close();
    }

    public void handleRequest(ServerSocket serverSocket){
        System.out.println("Server is listening in port: " + SERVER_PORT);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        socket = serverSocket.accept();
                        System.out.println(socket + "Connected");
                        dataInputStream = new DataInputStream(socket.getInputStream());
                        dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }

                    try {
                        boolean sqrtcheck = false;
                        input = dataInputStream.readUTF();
                        System.out.println("Input received:"+input);
                        String[] check = input.split(",");

                        if (check[0].equals("Calculator")){
                            if(check[1].split("\\+").length == 2){
                                String[] numberinput = check[1].split("\\+");
                                outputint = Integer.parseInt(numberinput[0]) + Integer.parseInt(numberinput[1]);
                            }
                            else if(check[1].split("-").length == 2){
                                String[] numberinput = check[1].split("-");
                                outputint = Integer.parseInt(numberinput[0]) - Integer.parseInt(numberinput[1]);
                            }
                            else if(check[1].split("\\*").length == 2){
                                String[] numberinput = check[1].split("\\*");
                                outputint = Integer.parseInt(numberinput[0]) * Integer.parseInt(numberinput[1]);
                            }
                            else if(check[1].split("/").length == 2){
                                String[] numberinput = check[1].split("/");
                                outputint = Integer.parseInt(numberinput[0]) / Integer.parseInt(numberinput[1]);
                            }
                            else if(check[1].split("mod").length == 2){
                                String[] numberinput = check[1].split("mod");
                                outputint = Integer.parseInt(numberinput[0]) % Integer.parseInt(numberinput[1]);
                            }
                            else if(check[1].split("^").length == 2){
                                String[] numberinput = check[1].split("^");
                                outputint = Integer.parseInt(numberinput[0]) ^ Integer.parseInt(numberinput[1]);
                            }
                            else if(input.split("sqrt").length == 2){
                                String[] numberinput = input.split("sqrt");
                                outputdouble = Math.sqrt(Double.parseDouble(numberinput[1]));
                                sqrtcheck = true;
                            }
                            if (sqrtcheck){
                                dataOutputStream.writeUTF(Double.toString(outputdouble));
                            }
                            else {
                                dataOutputStream.writeUTF(Integer.toString(outputint));
                            }
                        }
                        else if (check[0].equals("Logical Operation")){

                            String inputlogic = check[2]+check[3];
                            String logicoperation = check[1];

                            if (logicoperation.equals("AND")){
                                if (inputlogic.toUpperCase().equals("TT")){
                                    dataOutputStream.writeUTF("T");
                                }else if (inputlogic.toUpperCase().equals("TF") || inputlogic.toUpperCase().equals("FT")){
                                    dataOutputStream.writeUTF("F");
                                }else if(inputlogic.toUpperCase().equals("FF")){
                                    dataOutputStream.writeUTF("F");
                                }
                            }else if (logicoperation.equals("OR")){
                                if (inputlogic.toUpperCase().equals("TT")){
                                    dataOutputStream.writeUTF("T");
                                }else if (inputlogic.toUpperCase().equals("TF") || inputlogic.toUpperCase().equals("FT")){
                                    dataOutputStream.writeUTF("T");
                                }else if(inputlogic.toUpperCase().equals("FF")){
                                    dataOutputStream.writeUTF("F");
                                }
                            }else if (logicoperation.equals("NAND")){
                                if (inputlogic.toUpperCase().equals("TT")){
                                    dataOutputStream.writeUTF("F");
                                }else if (inputlogic.toUpperCase().equals("TF") || inputlogic.toUpperCase().equals("FT")){
                                    dataOutputStream.writeUTF("T");
                                }else if(inputlogic.toUpperCase().equals("FF")){
                                    dataOutputStream.writeUTF("T");
                                }
                            }else if (logicoperation.equals("NOR")){
                                if (inputlogic.toUpperCase().equals("TT")){
                                    dataOutputStream.writeUTF("F");
                                }else if (inputlogic.toUpperCase().equals("TF") || inputlogic.toUpperCase().equals("FT")){
                                    dataOutputStream.writeUTF("F");
                                }else if(inputlogic.toUpperCase().equals("FF")){
                                    dataOutputStream.writeUTF("T");
                                }
                            }else if (logicoperation.equals("XOR")){
                                if (inputlogic.toUpperCase().equals("TT")){
                                    dataOutputStream.writeUTF("F");
                                }else if (inputlogic.toUpperCase().equals("TF") || inputlogic.toUpperCase().equals("FT")){
                                    dataOutputStream.writeUTF("T");
                                }else if(inputlogic.toUpperCase().equals("FF")){
                                    dataOutputStream.writeUTF("F");
                                }
                            }
                        }
                        else if (check[0].equals("Number Base Converter")){

                            String inputtype = check[1];
                            String outputtype = check[2];
                            String out = "";
                            String input = check[3];
                            int decimal = 0;

                            if(inputtype.equals("B") || inputtype.equals("D") || inputtype.equals("O") || inputtype.equals("H")){

                                if(inputtype.equals("B"))
                                    decimal = Integer.parseInt(input,2);
                                else if(inputtype.equals("O"))
                                    decimal = Integer.parseInt(input,8);
                                else if (inputtype.equals("D"))
                                    decimal = Integer.parseInt(input);
                                else if(inputtype.equals("H"))
                                    decimal = Integer.parseInt(input,16);

                            }
                            if(outputtype.equals("B") || outputtype.equals("D") || outputtype.equals("O") || outputtype.equals("H")){

                                if(outputtype.equals("B"))
                                    out = Integer.toBinaryString(decimal);
                                else if(outputtype.equals("O"))
                                    out = Integer.toOctalString(decimal);
                                else if (outputtype.equals("D"))
                                    out = Integer.toString(decimal);
                                else if(outputtype.equals("H"))
                                    out = Integer.toHexString(decimal);

                            }
                            dataOutputStream.writeUTF(out);

                        }

                        dataOutputStream.flush();
                        stop();
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }

                }
            }
        });

        thread.start();
    }

    public static void main(String[] args)throws IOException {
        Server server = new Server();
        ServerSocket serverSocket = server.start();
        if(serverSocket != null){
            server.handleRequest(serverSocket);
        }
    }

}
