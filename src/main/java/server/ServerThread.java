package server;

import interaction.DataReader;
import interaction.DataWriter;
import protocol.Message;
import protocol.MessageWithFile;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import static phrases.Phrases.*;

public class ServerThread extends Thread {
    private final ServerSocket server;
    private final List<DataWriter> dataWriterList;
    private final Map<String,User> users;

    public ServerThread(ServerSocket server) {
        this.server = server;
        this.dataWriterList = new LinkedList<>();
        this.users = new HashMap<>();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = server.accept();
                DataReader dataReader = new DataReader(socket.getInputStream());
                DataWriter dataWriter = new DataWriter(socket.getOutputStream());

                boolean isAdded = false;
                while (!isAdded)
                if (dataReader.hasMessage()) {
                    MessageWithFile messageWithFile = dataReader.read();
                    String userName = messageWithFile.getMessage().getText();
                    String time = Server.simpleDateFormat.format(new Date());
                    if (users.containsKey(userName)) {
                        MessageWithFile messageToUser = new MessageWithFile(
                                new Message(time,SERVER.getPhrase(),NAME_IS_TAKEN.getPhrase(),null,null),
                                null);
                        dataWriter.write(messageToUser);
                    } else {
                        User user = new User(socket,userName);
                        users.put(userName,user);
                        dataWriterList.add(dataWriter);
                        ClientThread clientThread = new ClientThread(dataReader,dataWriter,dataWriterList,user);
                        clientThread.start();
                        MessageWithFile messageToUser = new MessageWithFile(
                                new Message(time,SERVER.getPhrase(),WELCOME.getPhrase() + userName,null,null),
                                null);
                        for (DataWriter writer : dataWriterList) {
                            writer.write(messageToUser);
                        }
                        isAdded = true;
                    }
                }
                // TODO
//                System.out.println(socket.isClosed());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public Map<String, User> getUsers() {
        return users;
    }
}
