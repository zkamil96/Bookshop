package com.example.javafx.Model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Book {
    private long id;
    private long authorId;
    private String title;
    private String publisher;
    private String category;
    private String releaseYear;
}
