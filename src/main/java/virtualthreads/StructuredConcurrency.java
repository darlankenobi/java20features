package virtualthreads;

import virtualthreads.examples.DogWithShutdownOnSuccess;

import java.time.Duration;
import java.time.Instant;

public class StructuredConcurrency {


    public static void main(String[] args) throws Exception {
        var begin = Instant.now();
        //DogWithStructuredTaskScope dog = DogWithStructuredTaskScope.readDog();
        DogWithShutdownOnSuccess dog = DogWithShutdownOnSuccess.readDog();
        var end = Instant.now();
        System.out.printf("Dog Image = %s%n", dog.message());
        System.out.printf("Time is = %d ms%n", Duration.between(begin, end).toMillis());
    }

}
