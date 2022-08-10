package com.example.springboot;

import com.example.springboot.Model.Author;
import com.example.springboot.Model.Book;
import com.example.springboot.Repository.AuthorRepository;
import com.example.springboot.Repository.BookRepository;
import com.example.springboot.Service.AuthorService;
import com.example.springboot.Service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class ApplicationTests {
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private BookRepository bookRepository;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookService bookService;
    Author author = new Author(1, "kamil", "zarebski", "male", new ArrayList<Book>());
    Book book = new Book(1, 1, "title", "publisher", "category", "year");

    @Test
    public void addAuthorTest() {
        when(authorRepository.save(author)).thenReturn(author);
        assertEquals(author, authorService.addAuthor(author));
    }

    @Test
    public void addBookTest() {
        when(bookRepository.save(book)).thenReturn(book);
        assertEquals(book, bookService.addBook(book, book.getAuthorId()));
    }

    @Test
    public void deleteAuthorTest() {
        when(authorRepository.findById(author.getId())).thenReturn(Optional.ofNullable(author));
        authorService.deleteAuthor(author.getId());
        verify(authorRepository, times(1)).deleteById(author.getId());
    }

    @Test
    public void deleteBookTest() {
        when(bookRepository.findById(book.getId())).thenReturn(Optional.ofNullable(book));
        bookService.deleteBook(book.getId());
        verify(bookRepository, times(1)).deleteById(book.getId());
    }

    @Test
    public void updateAuthorTest() {
        Author updatedAuthor = new Author(1, "adrian", "zarebski", "male", new ArrayList<Book>());
        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
        when(authorRepository.save(updatedAuthor)).thenReturn(updatedAuthor);
        assertEquals(updatedAuthor.getName(), "adrian");
    }

    @Test
    public void updateBookTest() {
        Book updatedBook = new Book(1, 1, "title2", "publisher", "category", "year");
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(updatedBook)).thenReturn(updatedBook);
        assertEquals(updatedBook.getTitle(), "title2");
    }

}
