package virtualthreads.examples;

import jdk.incubator.concurrent.StructuredTaskScope;

import javax.json.Json;
import javax.json.stream.JsonParser;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public record DogWithShutdownOnSuccess(String message, String status) {

    private static DogWithShutdownOnSuccess fromJson(String json) {
        try (JsonParser parser = Json.createParser(new StringReader(json))) {
            parser.next();
            var jsonObject = parser.getObject();
            return new DogWithShutdownOnSuccess(jsonObject.getString("message"), jsonObject.getString("status"));
        }
    }

    public static DogWithShutdownOnSuccess readDog() throws InterruptedException {
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<DogWithShutdownOnSuccess>()) {
            final Future<DogWithShutdownOnSuccess> futureA = scope.fork(DogWithShutdownOnSuccess::readDogFromA);
            final Future<DogWithShutdownOnSuccess> futureB = scope.fork(DogWithShutdownOnSuccess::readDogFromB);
            final Future<DogWithShutdownOnSuccess> futureC = scope.fork(DogWithShutdownOnSuccess::readDogFromC);

            scope.join();

            System.out.println("Status feature A " + futureA.state());
            System.out.println("Status feature B " + futureB.state());
            System.out.println("Status feature C " + futureC.state());

            return scope.result();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static DogWithShutdownOnSuccess readDogFromA() throws IOException, InterruptedException {
        System.out.printf("Thread name: %s, id: %d%n", Thread.currentThread(), Thread.currentThread().threadId());
        return readDog("A");
    }

    private static DogWithShutdownOnSuccess readDogFromB() throws IOException, InterruptedException {
        System.out.printf("Thread name: %s, id: %d%n", Thread.currentThread(), Thread.currentThread().threadId());
        return readDog("B");
    }

    private static DogWithShutdownOnSuccess readDogFromC() throws IOException, InterruptedException {
        System.out.printf("Thread name: %s, id: %d%n", Thread.currentThread(), Thread.currentThread().threadId());
        return readDog("C");
    }

    private static DogWithShutdownOnSuccess readDog(final String id) throws InterruptedException, IOException {
        final var server = "http://localhost:3000";
        final var uri = String.join("/", server, "dogs", id);
        System.out.printf("Request GET to: %s%n", uri);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .GET()
            .build();
        HttpResponse<String> response = client
            .send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return DogWithShutdownOnSuccess.fromJson(response.body());
        }
        throw new RuntimeException("Server unavailable");
    }

}
