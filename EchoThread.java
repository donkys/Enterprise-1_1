import java.io.*;
import java.net.*;
import java.util.Scanner;

class EchoThread extends Thread {
    private Socket connectionSocket;

    public EchoThread(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    private int getValidatedNumber(BufferedReader in, PrintWriter out, String prompt) throws IOException {
        while (true) {
            try {    
                out.println(prompt);
                String input = in.readLine();
                if (input.equals("")) return Integer.MIN_VALUE;
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                out.println("Invalid input, please enter a number.");
            }
        }
    }

    public void run() {
        try (BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
             PrintWriter outToClient = new PrintWriter(connectionSocket.getOutputStream(), true)) {
            
            while (true) {
                int num1 = getValidatedNumber(inFromClient, outToClient, "enter number 1 (to end just press enter): ");
                if (num1 == Integer.MIN_VALUE) break;

                int num2 = getValidatedNumber(inFromClient, outToClient, "enter number 2 (to end just press enter): ");
                if (num2 == Integer.MIN_VALUE) break;

                int sum = num1 + num2;
                outToClient.println("The result is " + sum);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                connectionSocket.close();
            } catch (IOException e) {
                System.out.println("Could not close the connection socket.");
            }
        }
    }
}
