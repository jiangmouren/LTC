package com.mycompany.app;

/**
 * Created by eljian on 10/18/2017.
 */

public class ThreadDemo1 extends Thread {
    //private Thread t;
    private String threadName;

    ThreadDemo1(String name) {
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

    public static void main(String args[]) {
        ThreadDemo1 T1 = new ThreadDemo1( "Thread-1");
        T1.start();

        ThreadDemo1 T2 = new ThreadDemo1( "Thread-2");
        T2.start();
    }
}
