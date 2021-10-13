package server;

import interaction.DataReader;
import interaction.DataWriter;
import protocol.Message;
import protocol.MessageWithFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static phrases.Phrases.*;

public class ClientThread extends Thread {
    private final DataReader dataReader;
    private final DataWriter dataWriter;
    private final List<DataWriter> dataWriterList;
    private final User user;

    public ClientThread(DataReader dataReader, DataWriter dataWriter, List<DataWriter> dataWriterList, User user) {
        this.dataReader = dataReader;
        this.dataWriter = dataWriter;
        this.dataWriterList = dataWriterList;
        this.user = user;
    }

    @Override
    public void run() {
        MessageWithFile messageWithFile = null;

        while (true) {
            if (dataReader.hasMessage()) {
                messageWithFile = dataReader.read();
            }
            if (messageWithFile != null) {
                String time = Server.simpleDateFormat.format(new Date());
                if (messageWithFile.getMessage().getText().trim().equals(EXIT.getPhrase())) {
                    messageWithFile = new MessageWithFile(
                            new Message(time,SERVER.getPhrase(),user.getName() + NAME_IS_TAKEN.getPhrase(),null,null),
                            null);
                    for (DataWriter writer : dataWriterList) {
                        writer.write(messageWithFile);
                    }
                    dataReader.close();
                    dataWriter.close();
                    dataWriterList.remove(dataWriter);
                    user.closeSocket();
                    interrupt();
                } else {
                    for (DataWriter writer : dataWriterList) {
                        messageWithFile.getMessage().setTime(time);
                        messageWithFile.getMessage().setName(user.getName());
                        writer.write(messageWithFile);
                    }
                }
                messageWithFile = null;
            }
        }
    }
}
