package virtualthreads;

import virtualthreads.api.Dog;

import java.time.Duration;
import java.time.Instant;

public class StructuredConcurrency {


    public static void main(String[] args) throws Exception {
        var begin = Instant.now();
        Dog dog = Dog.readDog();
        var end = Instant.now();
        System.out.println("Dog Image = " + dog.message());
        System.out.println("Time is = " + Duration.between(begin, end).toMillis() + "ms");
    }

}
