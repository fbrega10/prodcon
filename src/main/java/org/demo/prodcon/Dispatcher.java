package org.demo.prodcon;


import org.demo.adt.RingBuffer;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * A dispatcher is an entity that pulls messages from the incoming queue (from producers) and pushes
 * each message to the correlated output queue (by message-id).
 * Thread-safe class.
 */
public class Dispatcher extends Thread {
    private final String name;
    private final RingBuffer<Message> inputBuffer;
    private final List<RingBuffer<Message>> bufferList;
    private static final Random random = new Random();

    /**
     * @param name Name of the Dispatcher
     * @param inputBuffer The source of the messages
     * @param bufferList A list of the output ringBuffers to which write the incoming input message
     */
    public Dispatcher(String name, RingBuffer<Message> inputBuffer, List<RingBuffer<Message>> bufferList) {
        this.name = Objects.requireNonNull(name);
        this.inputBuffer = Objects.requireNonNull(inputBuffer);
        this.bufferList = Objects.requireNonNull(bufferList);
    }

    /**
     * Polls messages from the incoming queue and dispatches them to the related output queue, depending
     * on the message number.
     */
    @Override
    public void run() {
        while (true) {
            while (inputBuffer.isEmpty()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted " + this.name);
                    return;
                }
            }
            Message m = inputBuffer.pop();
            System.out.println("Read message : " + m.toString() + " from Dispatcher: " + this.name);
            Integer i = m.getChannel();

            RingBuffer<Message> current = bufferList.get(i);
            while (current.isFull()) {
                try {
                    Thread.sleep(random.nextInt(0, 20));
                } catch (InterruptedException e) {
                    System.out.println("Caught exception");
                    return;
                }
            }
            current.push(m);
            System.out.println(Utils.getCurrentTime() + " DISPATCHER - Pushing message : " + m + "  to channel " + i);
        }
    }
}
