package com.example.springboot.Controller;

import com.example.springboot.Model.Author;
import com.example.springboot.Model.Book;
import com.example.springboot.Service.AuthorService;
import com.example.springboot.Service.BookService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookshopController {
    Logger logger = LoggerFactory.getLogger(BookshopController.class);
    private final AuthorService authorService;
    private final BookService bookService;

    @GetMapping("/authors")
    public List<Author> getAuthors() {
        return authorService.getAuthors();
    }

    @GetMapping("/authors/{id}")
    public Author getSingleAuthor(@PathVariable long id) {
        return authorService.getSingleAuthor(id);
    }

    @GetMapping("/authors/{id}/books")
    public List<Book> getBooks(@PathVariable long id) {
        return bookService.getBooks(id);
    }

    @GetMapping("/authors/search")
    public List<Author> searchAuthor(@RequestParam(name = "search") String search) {
        return authorService.searchAuthor(search);
    }

    @PostMapping("/authors")
    public Author addAuthor(@RequestBody Author author) {
        return authorService.addAuthor(author);
    }

    @PostMapping("/authors/{id}/books")
    public Book addBook(@RequestBody Book book, @PathVariable long id) {
        return bookService.addBook(book, id);
    }

    @DeleteMapping("/authors/{id}")
    public void deleteAuthor(@PathVariable long id) {
        authorService.deleteAuthor(id);
    }

    @DeleteMapping("/authors/{id}/books")
    public void deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
    }

    @PutMapping("/authors")
    public Author updateAuthor(@RequestBody Author author) {
        return authorService.updateAuthor(author);
    }

    @PutMapping("/authors/{id}/books")
    public Book updateBook(@RequestBody Book book, @PathVariable long id) {
        return bookService.updateBook(book);
    }
}
