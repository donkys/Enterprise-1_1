import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient {
    public static void main(String argv[]) throws Exception {
        Socket clientSocket = new Socket("localhost", 1667);
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String serverPrompt = inFromServer.readLine();
            if (serverPrompt == null) break;
            System.out.println(serverPrompt);

            String userInput = scanner.nextLine();
            outToServer.println(userInput);

            if (inFromServer.ready()) {
                System.out.println(inFromServer.readLine());
            }
        }

        clientSocket.close();
    }
}
