package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class Main {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {



        Scanner sc = new Scanner(System.in);
        System.out.println("Rentre le nom d'un mangas");
        String nomMangas = sc.nextLine();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://kitsu.io/api/edge/anime?filter[text]=" + nomMangas))
                .setHeader("User-Agent",  "Java 11 HttpClient Bot")
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String result = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
        System.out.println(result);
    }
}