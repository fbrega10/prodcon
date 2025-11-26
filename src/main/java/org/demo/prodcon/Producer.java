package org.demo.prodcon;


import org.demo.adt.RingBuffer;

import java.util.Objects;
import java.util.Random;

/**
 * Thread-safe producer, pushes continuously messages to the related buffer.
 * Sleeps as the queue is full and wakes up again when some space is again available.
 */
public class Producer extends Thread {
    private final String name;
    private final RingBuffer<Message> buffer;
    private static final Random random = new Random();

    /**
     * @param name Name of the producer
     * @param buffer The related writing buffer
     */
    public Producer(String name, RingBuffer<Message> buffer) {
        this.name = Objects.requireNonNull(name);
        this.buffer = Objects.requireNonNull(buffer);
    }

    /**
     * In the run method the Producer pushes messages continuously if the queue is not empty.
     * If the queue is full then the Producer sleeps, waiting to be woken up by the Dispatcher.
     */
    @Override
    public void run() {
        while (true) {
            Random random = new Random();
            while (buffer.isFull()) {
                try {
                    System.out.println("PRODUCER SLEEPING, FULL BUFFER " + this.name);
                    Thread.sleep(random.nextInt(0, 20));
                } catch (InterruptedException e) {
                    System.out.println("Interrupted! producer : " + this.name);
                    return;
                }
            }
            Message m = new Message(name, "Writing as producer " + this.name, random.nextInt(0, 4));
            buffer.push(m);
            System.out.println(Utils.getCurrentTime() + " - PRODUCER " + this.name + " produced message : " + m);
        }
    }
}
