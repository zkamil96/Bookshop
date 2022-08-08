package com.example.springboot.Service;

import com.example.springboot.Model.Author;
import com.example.springboot.Model.Book;
import com.example.springboot.Repository.AuthorRepository;
import com.example.springboot.Repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> getBooks(long id) {
        Author byId = authorRepository.findById(id).orElseThrow();
        List<Book> books = byId.getBooks();
        return books;
    }

    public Book addBook(Book book, long id) {
        book.setAuthorId(id);
        return bookRepository.save(book);
    }

    public List<Author> searchAuthor(String search) {
        return authorRepository.findAuthorByNameStartingWithOrSurnameStartingWithOrGenderStartingWith(search, search, search);
    }
    @Transactional
    public Author updateAuthor(Author author) {
        Author authorEdited = authorRepository.findById(author.getId()).orElseThrow();
        authorEdited.setName(author.getName());
        authorEdited.setSurname(author.getSurname());
        authorEdited.setGender(author.getGender());
        return authorEdited;
    }

    @Transactional
    public Book updateBook(Book book) {
        Author author = authorRepository.findById(book.getAuthorId()).orElseThrow();
        Book editedBook = new Book();
        for(int i = 0; i < author.getBooks().size(); i++){
            if(book.getId() == author.getBooks().get(i).getId()){
                editedBook = author.getBooks().get(i);
            }
        }
        editedBook.setTitle(book.getTitle());
        editedBook.setPublisher(book.getPublisher());
        editedBook.setCategory(book.getCategory());
        editedBook.setReleaseYear(book.getReleaseYear());
        return editedBook;
    }
}
