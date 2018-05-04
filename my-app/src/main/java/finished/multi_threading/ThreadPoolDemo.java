package finished.multi_threading;

import java.util.concurrent.*;

/**
 * Created by eljian on 10/18/2017.
 */
public class ThreadPoolDemo {
    public static void main(String[] args){
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for(int i=0; i<10; i++){
            Runnable worker = new RunnableDemo("thread"+i);
            executor.execute(worker);
        }
        executor.shutdown();
        try {
            executor.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
