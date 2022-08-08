package com.example.springboot.Controller;

import com.example.springboot.Model.Author;
import com.example.springboot.Model.Book;
import com.example.springboot.Service.BookshopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookshopController {

    private final BookshopService bookshopService;

    @GetMapping("/authors")
    public List<Author> getAuthors(){
        return bookshopService.getAuthors();
    }
    @GetMapping("/authors/{id}")
    public Author getSingleAuthor(@PathVariable long id){
        return bookshopService.getSingleAuthor(id);
    }
    @GetMapping("/authors/{id}/books")
    public List<Book> getBooks(@PathVariable long id){
        return bookshopService.getBooks(id);
    }
    @GetMapping("/authors/search")
    public List<Author> searchAuthor(@RequestParam(name = "search") String search){
        return bookshopService.searchAuthor(search);
    }
    @PostMapping("/authors")
    public Author addAuthor(@RequestBody Author author){
        return  bookshopService.addAuthor(author);
    }
    @PostMapping("/authors/{id}/books")
    public Book addBook(@RequestBody Book book, @PathVariable long id){
        return  bookshopService.addBook(book, id);
    }
    @DeleteMapping("/authors/{id}")
    public void deleteAuthor(@PathVariable long id){
        bookshopService.deleteAuthor(id);
    }
    @DeleteMapping("/authors/{id}/books")
    public void deleteBook(@PathVariable long id){
        bookshopService.deleteBook(id);
    }
    @PutMapping("/authors")
    public Author updateAuthor(@RequestBody Author author){
        return bookshopService.updateAuthor(author);
    }
    @PutMapping("/authors/{id}/books")
    public Book updateBook(@RequestBody Book book, @PathVariable long id){
        return bookshopService.updateBook(book);
    }
}
