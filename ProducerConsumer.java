package main;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumer {

    public static void main(String[] args) {

        Object key = new Object();
        Queue<Integer> queue = new LinkedList<>();
        int size = 10;

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                int cont = 0;
                while (true) {

                    synchronized (key) {
                        try {
                            // semnalul rosu de a opri productia
                            while (queue.size() == size) {
                                key.wait();
                            }

                            queue.offer(cont++); // adaugam urmatorul numar in queue
                            key.notifyAll();

                            Thread.sleep(1000);
                            System.out.println(" MOMO producer, plate size=" + queue.size());

                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });

        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (key) {
                        try {
                            // red signal de a opri consumerul
                            while (queue.size() == 0) {
                                key.wait();
                            }

                            queue.poll(); // eliminam primul element din queue
                            key.notifyAll(); // notificam toate theadurile producer sa inceapa sa adauge in queue

                            Thread.sleep(800);
                            System.out.println(" MOMO Consumed, plate size=" + queue.size());

                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });

        producer.start();
        consumer.start();
    }

}
