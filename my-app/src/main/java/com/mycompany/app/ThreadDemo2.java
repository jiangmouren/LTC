package com.mycompany.app;

public class ThreadDemo2 implements Runnable{
    private String threadName;
    public ThreadDemo2(String name){
        this.threadName = name;
        System.out.println("Creating "+threadName);
    }

    public void run(){
        try{
            for(int i=4; i>0; i--){
                System.out.println("Thread: "+threadName+", "+i);
                //let thread sleep
                Thread.sleep(1500);
            }
        }catch(InterruptedException e){
            System.out.println("Thread "+threadName+" interrupted.");
        }
        System.out.println("Thread "+threadName+" exiting.");
    }

    public static void main(String[] args){
        ThreadDemo2 runnable1 = new ThreadDemo2("Thread-1");
        ThreadDemo2 runnable2 = new ThreadDemo2("Thread-2");
        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);
        t1.start();
        t2.start();
    }
}
