package com.example.javafx.Service;

import com.example.javafx.Model.Author;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class AuthorService {

    private static final String API_URL = "http://localhost:8080";
    HttpClient client = HttpClient.newHttpClient();
    ObjectMapper mapper = new ObjectMapper();

    public ObservableList<Author> getAuthors() {
        String url = API_URL + "/authors";
        ObservableList<Author> observableAuthorList = FXCollections.observableArrayList();
        try {
            getRequest(url, observableAuthorList);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
        return observableAuthorList;
    }

    public void deleteAuthor(long id) {
        String url = API_URL + "/authors/" + id;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .DELETE()
                    .header("accept", "application/json")
                    .uri(URI.create(url))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void addAuthor(Author author) {
        String url = API_URL + "/authors";
        try {
            String json = mapper.writeValueAsString(author);
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .header("Content-Type", "application/json")
                    .uri(URI.create(url))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Author> searchAuthor(String author) {
        String url = API_URL + "/authors/search?search=" + author;
        ObservableList<Author> observableAuthorList = FXCollections.observableArrayList();
        try {
            getRequest(url, observableAuthorList);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return observableAuthorList;
    }

    private void getRequest(String url, ObservableList<Author> observableAuthorList) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        List<Author> authors = mapper.readValue(response.body(), new TypeReference<>() {
        });
        observableAuthorList.addAll(authors);
    }
}
