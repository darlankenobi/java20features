public class Main {

    public static void main(String[] args) throws InterruptedException {
        var platformThread = Thread.ofPlatform().unstarted(() -> System.out.println(Thread.currentThread()));
        platformThread.start();
        platformThread.join();

        var virtualThread = Thread.ofVirtual().unstarted(() -> System.out.println(Thread.currentThread()));
        virtualThread.start();
        virtualThread.join();
    }

}