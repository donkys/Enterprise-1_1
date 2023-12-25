import java.io.*;
import java.net.*;
public class TCPConcurrentServer {
    public static void main(String argv[]) {
        try {
            ServerSocket welcomeSocket = new ServerSocket(1667);
            System.out.println("Waiting for client connection at port number 1667");

            while (true) {
                Socket connectionSocket = welcomeSocket.accept();
                EchoThread echoThread = new EchoThread(connectionSocket);
                echoThread.start();
            }
        } catch (IOException e) {
            System.out.println("Cannot create a welcome socket: " + e.getMessage());
        }
    }
}