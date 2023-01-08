package main;

class Printer implements Runnable {
    static int counter = 1;
    int reminder;
    static Object lock = new Object();

    Printer(int reminder) {
        this.reminder = reminder;
    }

    public void printer() {
        System.out.println(Thread.currentThread().getName() + " " + counter++);
    }

    @Override
    public void run() {
        for (int i = 1; i < 20; i++) {

            synchronized (lock) {
                while (counter % 2 != reminder) {
                    try {
//                        System.out.println("😀 PAR " + Thread.currentThread().getName() + ": " + counter);

                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                printer();
                lock.notifyAll();
            }
        }
    }
}

public class PrintingOddAndEvenNumberSeparatedThreads {

    public static void main(String[] args) {

        Thread even = new Thread(new Printer(0), "(Thread-par)");
        Thread odd = new Thread(new Printer(1), "(Thread-inpar)");

        even.start();
        odd.start();
    }
}
