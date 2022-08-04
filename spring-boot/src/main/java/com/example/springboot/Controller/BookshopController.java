package com.example.springboot.Controller;

import com.example.springboot.Model.Author;
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

    @PostMapping("/authors")
    public Author addAuthor(@RequestBody Author author){
        return  bookshopService.addAuthor(author);
    }

    @DeleteMapping("/author{id}")
    public void deleteAuthor(@PathVariable long id){
        bookshopService.deleteAuthor(id);
    }
}
