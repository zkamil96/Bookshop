package com.example.javafx.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Author {
    private long id;
    private String name;
    private String surname;
    private String gender;
    private List<Book> books;

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                '}';
    }


}
