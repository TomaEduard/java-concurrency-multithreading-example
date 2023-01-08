package main;


public class Restaurant {

    public static void main(String[] args) {

        final String lock = "LOCK";

        Runnable runnable = new Runnable() {
            public void prepareFood() {

                synchronized (lock) {
                    System.out.println("Food preparing: STARTED " + Thread.currentThread().getName());

                    try {
                        Thread.sleep(3000);

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println("Food preparing: DONE " + Thread.currentThread().getName());
                }
            }

            @Override
            public void run() {
                System.out.println("\n\nOrder taken " + Thread.currentThread().getName());
                prepareFood();
            }

        };

        Thread t1 = new Thread(runnable);
        Thread t2= new Thread(runnable);

        t1.start();
        t2.start();
    }
}