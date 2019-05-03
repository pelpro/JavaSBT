import java.util.concurrent.LinkedBlockingQueue;

public class FixedThreadPool implements ThreadPool {
    public class Worker extends Thread {
        public void run() {
            Runnable task;
            while (true) {
                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    task = tasks.poll();
                }

                try {
                    task.run();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int countThreads;
    private Worker[] threads;
    private LinkedBlockingQueue<Runnable> tasks;


    FixedThreadPool(int countThreads) {
        this.countThreads = countThreads;
        threads = new Worker[countThreads];
        tasks = new LinkedBlockingQueue<Runnable>();
    }

    public void start() {
        for (int i = 0; i < countThreads; i++) {
            threads[i] = new Worker();
            threads[i].run();
        }
    }

    public void execute(Runnable runnable) {
        synchronized (tasks) {
            tasks.add(runnable);
            tasks.notify();
        }
    }

}