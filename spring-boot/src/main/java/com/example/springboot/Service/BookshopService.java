package com.example.springboot.Service;

import com.example.springboot.Model.Author;
import com.example.springboot.Model.Book;
import com.example.springboot.Repository.AuthorRepository;
import com.example.springboot.Repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookshopService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    public void deleteAuthor(long id) {
        authorRepository.deleteById(id);
    }

    public Author getSingleAuthor(long id) {
        return authorRepository.findById(id).orElseThrow();
    }
}
