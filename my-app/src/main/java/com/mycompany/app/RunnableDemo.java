package com.mycompany.app;

/**
 * Created by eljian on 10/18/2017.
 */
class RunnableDemo implements Runnable {
    private String threadName;

    RunnableDemo( String name) {
        threadName = name;
        System.out.println("Creating " +  threadName );
    }

    public void run() {
        try {
            for(int i = 4; i > 0; i--) {
                System.out.println("Thread: " + threadName + ", " + i);
                // Let the thread sleep for a while.
                Thread.sleep(1500);
            }
        }catch (InterruptedException e) {
            System.out.println("Thread " +  threadName + " interrupted.");
        }
        System.out.println("Thread " +  threadName + " exiting.");
    }

    //public static void main(String args[]) {
    //    RunnableDemo R1 = new RunnableDemo( "Thread-1");
    //    Thread t1 = new Thread (R1, R1.threadName);
    //    t1.start();

    //    RunnableDemo R2 = new RunnableDemo( "Thread-2");
    //    Thread t2 = new Thread (R2, R2.threadName);
    //    t2.start();
    //}
}