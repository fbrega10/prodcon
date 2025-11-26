package org.demo.prodcon;

import java.util.Objects;

/**
 * Entity that represents a single message
 */
public class Message {
    private final String sender;
    private final String text;
    private final Integer channel;

    /**
     * @param sender Sender name (text)
     * @param text Message content (text)
     * @param channel Message related channel (text)
     */
    public Message(String sender, String text, Integer channel) {
        this.sender = Objects.requireNonNull(sender);
        this.text = Objects.requireNonNull(text);
        this.channel = Objects.requireNonNull(channel);
    }

    /**
     * @return an integer representing the channel number
     */
    public Integer getChannel(){
        return this.channel;
    }

    /**
     * @return formatted message, human printable
     */
    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", text='" + text + '\'' +
                ", channel=" + channel +
                '}';
    }
}
