package virtualthreads.examples;

import jdk.incubator.concurrent.StructuredTaskScope;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Future;

public record CustomStructuredTaskScope() {

    public static BigDecimal readTotalBalance() throws InterruptedException {
        try (var scope = new TotalBalanceScope()) {
            scope.fork(CustomStructuredTaskScope::readDogFromA);
            scope.fork(CustomStructuredTaskScope::readDogFromB);
            scope.fork(CustomStructuredTaskScope::readDogFromC);
            scope.join();
            return scope.totalBalance();
        }
    }

    private static Balance readDogFromA() {
        System.out.printf("Thread name: %s, id: %d%n", Thread.currentThread(), Thread.currentThread().threadId());
        return readBalance("A");
    }

    private static Balance readDogFromB() {
        System.out.printf("Thread name: %s, id: %d%n", Thread.currentThread(), Thread.currentThread().threadId());
        return readBalance("B");
    }

    private static Balance readDogFromC() {
        System.out.printf("Thread name: %s, id: %d%n", Thread.currentThread(), Thread.currentThread().threadId());
        return readBalance("C");
    }

    private static Balance readBalance(String name) {
        try {
            long timeout = (long) (Math.random() * 1000) + 100;
            final var balance = BigDecimal.valueOf((Math.random() * 100) + 5)
                .setScale(2, RoundingMode.HALF_EVEN);
            System.out.printf("Task %d executing for %s ms%n", Thread.currentThread().threadId(), timeout);
            Thread.sleep(timeout);
            System.out.printf("Task %d done!%n", Thread.currentThread().threadId());
            System.out.printf("Balance is %s%n", balance);
            return new Balance(name, balance);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public record Balance(String name, BigDecimal balance) {

    }

    public static class TotalBalanceScope extends StructuredTaskScope<Balance> {

        private final Collection<Balance> balances = new ConcurrentLinkedDeque<>();
        private final Collection<Throwable> exceptions = new ConcurrentLinkedDeque<>();

        @Override
        protected void handleComplete(final Future<Balance> future) {
            switch (future.state()) {
                case RUNNING -> throw new IllegalStateException("Future is still running...");
                case SUCCESS -> this.balances.add(future.resultNow());
                case FAILED -> this.exceptions.add(future.exceptionNow());
                case CANCELLED -> {
                }
            }
        }

        public BigDecimal totalBalance() {
            if (!exceptions.isEmpty()) {
                throw exceptions();
            }
            return this.balances.stream()
                .map(Balance::balance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        public BalanceException exceptions() {
            BalanceException exception = new BalanceException();
            this.exceptions.forEach(exception::addSuppressed);
            return exception;
        }

    }

}
