package com.example.javafx.Service;

import com.example.javafx.Model.Book;
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

public class BookService {

    private static final String API_URL = "http://localhost:8080";
    HttpClient client = HttpClient.newHttpClient();
    ObjectMapper mapper = new ObjectMapper();

    public ObservableList<Book> getBooks(long id) {
        String url = API_URL + "/authors/" + id + "/books";
        ObservableList<Book> observableBookList = FXCollections.observableArrayList();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("accept", "application/json")
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            List<Book> book = mapper.readValue(response.body(), new TypeReference<>() {
            });
            observableBookList.addAll(book);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return observableBookList;
    }

    public void deleteBook(long id) {
        String url = API_URL + "/authors/" + id + "/books";
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

    public void addBook(Book book, long id) {
        String url = API_URL + "/authors/" + id + "/books";
        try {
            String json = mapper.writeValueAsString(book);
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
}
