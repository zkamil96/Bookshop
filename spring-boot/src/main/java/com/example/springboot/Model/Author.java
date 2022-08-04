package com.example.springboot.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "authorId")
    private List<Book> books;

}
