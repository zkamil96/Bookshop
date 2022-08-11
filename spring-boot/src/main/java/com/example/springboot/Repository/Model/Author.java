package com.example.springboot.Repository.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    private String gender;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "authorId", updatable = false, insertable = false)
    private List<Book> books;

}
