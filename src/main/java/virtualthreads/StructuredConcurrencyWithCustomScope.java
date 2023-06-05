package virtualthreads;

import virtualthreads.examples.CustomStructuredTaskScope;

import java.time.Duration;
import java.time.Instant;

public class StructuredConcurrencyWithCustomScope {


    public static void main(String[] args) throws Exception {
        var begin = Instant.now();
        var end = Instant.now();
        final var totalBalance = CustomStructuredTaskScope.readTotalBalance();
        System.out.printf("Total balance = %s%n", totalBalance.toString());
        System.out.printf("Time is = %d ms%n", Duration.between(begin, end).toMillis());
    }

}
