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
import java.util.concurrent.Future;

public record DogWithStructuredTaskScope(String message, String status) {

    private static DogWithStructuredTaskScope fromJson(String json) {
        try (JsonParser parser = Json.createParser(new StringReader(json))) {
            parser.next();
            var jsonObject = parser.getObject();
            return new DogWithStructuredTaskScope(jsonObject.getString("message"), jsonObject.getString("status"));
        }
    }

    public static DogWithStructuredTaskScope readDog() throws InterruptedException {
        try (var scope = new StructuredTaskScope<DogWithStructuredTaskScope>()) {
            final Future<DogWithStructuredTaskScope> futureA = scope.fork(DogWithStructuredTaskScope::readDogFromA);
            scope.join();
            return futureA.resultNow();
        }
    }

    private static DogWithStructuredTaskScope readDogFromA() throws IOException, InterruptedException {
        System.out.printf("Thread name: %s, id: %d%n", Thread.currentThread(), Thread.currentThread().threadId());
        return readDog("A");
    }

    private static DogWithStructuredTaskScope readDog(final String id) throws InterruptedException, IOException {
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
            return DogWithStructuredTaskScope.fromJson(response.body());
        }
        throw new RuntimeException("Server unavailable");
    }

}
