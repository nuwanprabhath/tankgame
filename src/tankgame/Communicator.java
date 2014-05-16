/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankgame;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.ArrayList;


/*
 This class is used to communicate with the server.
 */
public class Communicator {

    private BufferedWriter output; // outgoing
    private BufferedReader input; //incoming
    private final int serverPort = 6000; //server port
    private final int clientPort = 7000; //client port
    private final String IPaddress = "localhost"; // IP of the server
    private Socket clientSocket;
    private Socket serverSocket;
    private ServerSocket ServerSocketForClient;
    private static Communicator comm = new Communicator();

    private ArrayList sendBuffer = new ArrayList();

    private Communicator() {
    }

    public static Communicator GetInstance() {

        return comm;
    }

    public String receiveMessage() {
        boolean error = false;
        clientSocket = null;
        String readLine = "#";

        try {
            ServerSocketForClient = new ServerSocket(clientPort);

            clientSocket = ServerSocketForClient.accept();
            if (ServerSocketForClient.isBound()) {
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while (!input.ready()) {
                    Thread.sleep(500);
                }
                readLine = input.readLine();
                input.close();
                clientSocket.close();
                ServerSocketForClient.close();
                error = false;
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Server Communication(recieving) Failed " + e.getMessage());
            error = true;
            clientSocket.close();
            ServerSocketForClient.close();
        } finally {

            if (clientSocket != null) {
                if (clientSocket.isConnected()) {
                    try {
                        clientSocket.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if (error) {

                this.receiveMessage();
            }
            return readLine;
        }
    }

    public String sendToServer(String msg) {

        try {
            serverSocket = new Socket(IPaddress, serverPort);
            if (this.serverSocket.isConnected()) {
                this.output = new BufferedWriter(new OutputStreamWriter(this.serverSocket.getOutputStream()));
                output.write(msg);
                output.close();
                serverSocket.close();
                System.out.println("msg: " + msg + " sent to server");
            }

        } catch (Exception e) {
            System.out.println("Server Communication(sending) Failed for " + msg + " " + e.getMessage());
        } finally {
        }

        return null;
    }

}
