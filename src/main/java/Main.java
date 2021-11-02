import server.Server;

import java.io.IOException;

public class Main {
    public static final int PORT = 60228;

    public static void main(String[] args) {
        Server server = new Server(PORT);
        try {
            server.start();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
