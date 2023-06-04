package virtualthreads;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualThreads {

    public static void main(String[] args) throws InterruptedException {
        final var pool = 10000;
        final var numberOfThreads = 10000;
        VirtualThreads virtualThreads = new VirtualThreads();
        //ExecutorService executorService = Executors.newFixedThreadPool(pool);
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        var begin = Instant.now();
        virtualThreads.executeTask(numberOfThreads, executorService);
        var end = Instant.now();
        System.out.println("-------------------- Done ----------------------");
        System.out.printf("This code ran a total of %d tasks in %d seconds",
            numberOfThreads,
            Duration.between(begin, end).toSeconds());
    }

    public Runnable createTask() {
        return () -> {
            try {
                long timeout = (long) (Math.random() * 10000) + 100;
                System.out.printf("Task %d executing for %s", Thread.currentThread().threadId(), timeout);
                Thread.sleep(timeout);
                System.out.printf("Task %d done!%n", Thread.currentThread().threadId());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public void executeTask(int taskInstances, ExecutorService executorService) {
        try (ExecutorService executor = executorService) {
            for (int i = 0; i < taskInstances; i++) {
                executor.execute(createTask());
            }
        }
    }

}
