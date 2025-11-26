package org.demo.prodcon;

import org.demo.adt.RingBuffer;

import java.util.Objects;
import java.util.Random;

/**
 * Thread-safe class that represents a Consumer, constantly polling from a ringBuffer incoming messages.
 * It blocks whenever there's an empty queue and sleeps until woken up by a related dispatcher filling up
 * the queue again.
 */
public class Consumer extends Thread {
    private final String name;
    private final RingBuffer<Message> ringBuffer;
    private static final Random random = new Random();

    /**
     * @param name Name of the consumer
     * @param ringBuffer Buffer from which it polls messages
     */
    public Consumer(String name, RingBuffer<Message> ringBuffer) {
        this.name = Objects.requireNonNull(name);
        this.ringBuffer = Objects.requireNonNull(ringBuffer);
    }

    /**
     * Overrides superclass run method (thread), sleeps if the queue is empty, otherwise it pulls each message from the
     * queue. Thread-safe method.
     */
    @Override
    public void run() {
        while (true) {
            while (ringBuffer.isEmpty()) {
                try {
                    System.out.println("CONSUMER SLEEPING " + this.name);
                    Thread.sleep(random.nextInt(0, 20));
                } catch (InterruptedException e) {
                    System.out.println("Caught interrupted - CONSUMER " + this.name);
                    return;
                }
            }
            Message m = ringBuffer.pop();
            System.out.println(Utils.getCurrentTime() + "CONSUMER " + this.name + " - consumed message " + m.toString());
        }
    }
}
