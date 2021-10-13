package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class Server {
    private final int port;
    public static List<ServerThread> connectionList = new LinkedList<>();
    public static SimpleDateFormat simpleDateFormat;

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        String pattern = "yyyy-MM-dd'T'HH:mm:ssZ";
        simpleDateFormat = new SimpleDateFormat(pattern);
        try (ServerSocket server = new ServerSocket(port)) {
            ServerThread serverThread = new ServerThread(server);
            serverThread.start();

            while (serverThread.isAlive()) {
                Thread.sleep(1000);
                System.out.println(serverThread.getUsers().keySet());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
