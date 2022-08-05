package com.example.javafx.Service;

import com.example.javafx.Model.Author;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.net.http.HttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BookshopService {
    private static final String GET_AUTHORS_URL = "http://localhost:8080/authors";

    public ObservableList<Author> getAuthors() {
        ObservableList<Author> observableAuthorList = FXCollections.observableArrayList();
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("accept", "application/json")
                    .uri(URI.create(GET_AUTHORS_URL))
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

    public Author getSingleAuthor(long id){
        String single_Author_URL = GET_AUTHORS_URL + "/" + id;
        Author author = new Author();
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("accept", "application/json")
                    .uri(URI.create(single_Author_URL))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            author = mapper.readValue(response.body(), new TypeReference<Author>() {});
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return author;
    }
}
