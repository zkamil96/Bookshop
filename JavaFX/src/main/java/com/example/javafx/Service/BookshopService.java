package com.example.javafx.Service;

import com.example.javafx.Model.Author;
import com.example.javafx.Model.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.net.http.HttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BookshopService {
    private static final String API_URL = "http://localhost:8080";
/*    private static final String GET_AUTHORS_URL = "http://localhost:8080/authors";
    private static final String GET_BOOKS_URL = "http://localhost:8080/authors/books";
    private static final String DELETE_AUTHORS_URL = "http://localhost:8080/author";
    private static final String DELETE_BOOK_URL = "http://localhost:8080/authors/books";
    private static final String ADD_AUTHOR_URL = "http://localhost:8080/authors";
    private static final String ADD_BOOK_URL = "http://localhost:8080/authors/books";*/

    public ObservableList<Author> getAuthors() {
        String url = API_URL  + "/authors";
        ObservableList<Author> observableAuthorList = FXCollections.observableArrayList();
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("accept", "application/json")
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            List<Author> authors = mapper.readValue(response.body(), new TypeReference<>() {});
            observableAuthorList.addAll(authors);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return observableAuthorList;
    }

    public List<Book> getBooks(long id){
        String url = API_URL  + "/authors/" + id + "/books";
        List<Book> book;
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("accept", "application/json")
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            book = mapper.readValue(response.body(), new TypeReference<>() {});
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return book;
    }

    public void deleteAuthor(long id){
        String url = API_URL  + "/authors/" + id;
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .DELETE()
                    .header("accept", "application/json")
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBook(long id){
        String url = API_URL  + "/authors/" + id + "/books";
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .DELETE()
                    .header("accept", "application/json")
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void addAuthor(Author author) {
        String url = API_URL  + "/authors";
        try{
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(author);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .header("Content-Type", "application/json")
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void addBook(Book book, long id) {
        String url = API_URL  + "/authors/" + id + "/books";
        try{
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(book);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .header("Content-Type", "application/json")
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Author> searchAuthor(String author){
        String url = API_URL  + "/authors/search?search=" + author;
        List<Author> authorsList = new ArrayList<>();
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("accept", "application/json")
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            List<Author> authors = mapper.readValue(response.body(), new TypeReference<>() {});
            authorsList.addAll(authors);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return authorsList;
    }
}
