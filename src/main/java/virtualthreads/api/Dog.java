package virtualthreads.api;

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

public record Dog(String message, String status) {

    public static Dog fromJson(String json) {
        try (JsonParser parser = Json.createParser(new StringReader(json))) {
            parser.next();
            var jsonObject = parser.getObject();
            return new Dog(jsonObject.getString("message"), jsonObject.getString("status"));
        }
    }

    public static Dog readDog() throws InterruptedException, IOException {
        try (var scope = new StructuredTaskScope<Dog>()) {
            final Future<Dog> futureA = scope.fork(Dog::readDogFromA);
            scope.join();
            final Dog dogA = futureA.resultNow();
            return dogA;
        }
    }

    public static Dog readDogFromA() throws InterruptedException, IOException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:3000/api/breeds/image/random"))
            .GET()
            .build();
        HttpResponse<String> response = client
            .send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return Dog.fromJson(response.body());
        }
        System.out.println(response.statusCode());
        throw new RuntimeException("Server unavailable");
    }

}
