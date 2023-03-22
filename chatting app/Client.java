import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private  Socket socket;
    private  BufferedReader reader;
    private  PrintWriter writer;

    public Client() {
        try {
            System.out.println("Sending Request to server.");
            socket = new Socket("127.0.0.1", 5757);
            System.out.println("Connection is established.");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            startReading();
            startWriting();
        } catch (IOException e) {
            System.out.println("An error occurred while setting up the client: " + e.getMessage());
            return;
        }
    }

    public void startReading() {
        // Thread to read messages from the server
        Runnable readTask = () -> {
            System.out.println("Reader started..");
            while (true) {
                try {
                    String message = reader.readLine();
                    if (message == null || message.equals("quit")) {
                        System.out.println("Server terminated the chat");
                        break;
                    }
                    System.out.println("Server: " + message);
                } catch (IOException e) {
                    System.out.println("An error occurred while reading messages from the server: " + e.getMessage());
                }
            }
        };
        new Thread(readTask).start();
    }

    public void startWriting() {
        // Thread to send messages to the server
        Runnable writeTask = () -> {
            System.out.println("Writer started..");
            try (BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
                while (true) {
                    String message = consoleReader.readLine();
                    if (message == null || message.equalsIgnoreCase("quit")) {
                        writer.println("quit");
                        break;
                    }
                    writer.println(message);
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading messages from the console: " + e.getMessage());
            }
        };
        new Thread(writeTask).start();
    }

    public static void main(String[] args) {
        System.out.println("This is the client.");
        new Client();
    }
}
