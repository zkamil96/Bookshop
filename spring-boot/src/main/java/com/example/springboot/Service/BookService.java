package com.example.springboot.Service;

import com.example.springboot.Model.Author;
import com.example.springboot.Model.Book;
import com.example.springboot.Repository.AuthorRepository;
import com.example.springboot.Repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public void deleteBook(long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            logger.error("Author not found for this id");
            throw new NoSuchElementException("Author not found for this id::" + id);
        }
        logger.info("Book has been deleted");
        bookRepository.deleteById(id);
    }

    public List<Book> getBooks(long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) {
            logger.error("Author not found for this id");
            throw new NoSuchElementException("Author not found for this id::" + id);
        }
        Author authorExist = author.get();
        if (authorExist.getBooks().size() == 0) {
            logger.warn("Author does not have books");
        } else {
            logger.info("Books has been found");
        }
        return authorExist.getBooks();
    }

    public Book addBook(Book book, long id) {
        book.setAuthorId(id);
        logger.info("Book has been saved");
        return bookRepository.save(book);
    }

    public Book updateBook(Book book) {
        Optional<Book> optionalBook = bookRepository.findById(book.getId());
        if (optionalBook.isEmpty()) {
            logger.error("Book not found for this id");
            throw new NoSuchElementException("Book not found for this id::" + book.getId());
        }
        logger.info("Book has been found");
        Book editedBook = optionalBook.get();
        editedBook.setTitle(book.getTitle());
        editedBook.setPublisher(book.getPublisher());
        editedBook.setCategory(book.getCategory());
        editedBook.setReleaseYear(book.getReleaseYear());
        logger.info("Book has been updated");
        return bookRepository.save(editedBook);
    }
}
