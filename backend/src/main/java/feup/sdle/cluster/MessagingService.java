package feup.sdle.cluster;

import feup.sdle.message.Message;

import java.util.concurrent.ArrayBlockingQueue;

public class MessagingService {
    protected ArrayBlockingQueue<Message.MessageFormat> messages;

    public MessagingService() {
        this.messages = new ArrayBlockingQueue<Message.MessageFormat>(128);
    }

    public void addToQueue(Message.MessageFormat message) {
        this.messages.add(message);
    }
}
