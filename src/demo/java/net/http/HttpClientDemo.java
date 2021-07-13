package demo.java.net.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class HttpClientDemo {

    public static void main(String[] args) {
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpResponse<String> response = null;
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("http://www.example.com/")).build();
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            return;
        }

        // HTTP允许重复的Header，因此一个Header可对应多个Value:
        Map<String, List<String>> headers = response.headers().map();
        System.out.println("*** response.headers ***");
        for (String header : headers.keySet()) {
            System.out.println(header + ": " + headers.get(header).get(0));
        }

        System.out.println("\n*** response.body ***");
        System.out.println(response.body());
    }

}
