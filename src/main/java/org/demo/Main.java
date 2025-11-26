package org.demo;

import org.demo.adt.RingBuffer;
import org.demo.prodcon.Consumer;
import org.demo.prodcon.Dispatcher;
import org.demo.prodcon.Message;
import org.demo.prodcon.Producer;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

        RingBuffer<Message> commonBuffer = new RingBuffer<>();
        Producer firstProducer = new Producer("PRODUCER1", commonBuffer);
        Producer secondProducer = new Producer("PRODUCER2", commonBuffer);

        RingBuffer<Message> first = new RingBuffer<>();
        RingBuffer<Message> second = new RingBuffer<>();
        RingBuffer<Message> third = new RingBuffer<>();
        RingBuffer<Message> fourth = new RingBuffer<>();

        Dispatcher dispatcher = new Dispatcher("DISPATCHER", commonBuffer, Arrays.asList(first, second, third, fourth));
        Consumer consumer = new Consumer("CONSUMER-0", first);
        Consumer consumer2 = new Consumer("CONSUMER-1", second);
        Consumer consumer3 = new Consumer("CONSUMER-2", third);
        Consumer consumer4 = new Consumer("CONSUMER-3", fourth);

        executorService.submit(firstProducer::start);
        executorService.submit(secondProducer::start);
        executorService.submit(dispatcher::start);
        executorService.submit(consumer::start);
        executorService.submit(consumer2::start);
        executorService.submit(consumer3::start);
        executorService.submit(consumer4::start);

        executorService.awaitTermination(100, TimeUnit.SECONDS);
        executorService.close();
    }
}