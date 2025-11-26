package org.demo.adt;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T> Generic class representing a RingBuffer ADT (Abstract Data Structure).
 *           From wiki:
 *           In computer science, a circular buffer, circular queue, cyclic buffer or ring buffer is
 *           a data structure that uses a single, fixed-size buffer as if it were connected end-to-end.
 *           This structure lends itself easily to buffering data streams.
 */
public class RingBuffer<T> {
    private final List<T> buffer;
    private int head, tail, counter;
    private final int capacity;

    /**
     * @param initialCapacity Initial slots of the buffer, the related list is IMMUTABLE.
     */
    public RingBuffer(int initialCapacity) {
        this.buffer = new ArrayList<>(initialCapacity);
        for (int i = 0; i < initialCapacity; ++i)
            this.buffer.add(null);
        this.head = this.tail = this.counter = 0;
        this.capacity = initialCapacity;
    }

    /**
     * The default constructor holds up to 10 slots.
     */
    public RingBuffer() {
        this(10);
    }

    /**
     * @param t T is the generic item type
     * @return returns the RingBuffer<T> itself, making it possible to use the method multiple
     * times sequentially.
     */
    public synchronized RingBuffer<T> push(T t) {
        if (this.isFull())
            return this;
        this.buffer.set(head, t);
        this.head = (head + 1) % capacity;
        ++counter;
        return this;
    }

    /**
     * @return Pops the item currently pointed by the tail of the circular buffer.
     */
    public synchronized T pop() {
        if (this.isEmpty())
            return null;
        T t = this.buffer.get(tail);
        this.buffer.set(tail, null);
        tail = (tail + 1) % capacity;
        --counter;
        return t;
    }

    /**
     * @return Visits the tail without removing it.
     */
    public T peek() {
        return this.isEmpty() ? null : this.buffer.get(tail);
    }

    /**
     * @return a boolean indicating whether the buffer has no capacity
     */
    public boolean isFull() {
        return counter == capacity;
    }

    /**
     * @return The current queue capacity
     */
    public int size() {
        return this.counter;
    }

    public boolean isEmpty() {
        return this.counter == 0;
    }

    /**
     * @return Human string visualizer representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for (int i = 0; i < this.counter; ++i) {
            if (i > 0)
                sb.append(", ");
            sb.append(this.buffer.get(i)).append(" ");
        }
        sb.append("]");
        return sb.toString();
    }
}
