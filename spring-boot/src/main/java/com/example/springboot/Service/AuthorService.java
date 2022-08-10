package com.example.springboot.Service;

import com.example.springboot.Model.Author;
import com.example.springboot.Repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {
    Logger logger = LoggerFactory.getLogger(AuthorService.class);
    private final AuthorRepository authorRepository;

    public List<Author> getAuthors() {

        List<Author> authorsList = authorRepository.findAll();
        if (authorsList.size() == 0) {
            logger.warn("There is no authors in database");
        } else {
            logger.info("Authors has been found");
        }
        return authorsList;
    }

    public Author addAuthor(Author author) {
        logger.info("Author has been saved");
        return authorRepository.save(author);
    }

    public void deleteAuthor(long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) {
            logger.error("Author not found for this id");
            throw new NoSuchElementException("Author not found for this id::" + id);
        }
        logger.info("Author has been deleted");
        authorRepository.deleteById(id);
    }

    public Author getSingleAuthor(long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) {
            logger.error("Author not found for this id");
            throw new NoSuchElementException("Author not found for this id::" + id);
        }
        logger.info("Author has been found");
        return author.get();
    }

    public List<Author> searchAuthor(String search) {
        List<Author> searchAuthorList = authorRepository.findAuthorByNameStartingWithOrSurnameStartingWithOrGenderStartingWith(search, search, search);
        if (searchAuthorList.size() == 0) {
            logger.warn("There is no authors with the search term");
        } else {
            logger.info("Searched author has been found");
        }
        return searchAuthorList;
    }

    public Author updateAuthor(Author author) {
        Optional<Author> optionalAuthor = authorRepository.findById(author.getId());
        if (optionalAuthor.isEmpty()) {
            logger.error("Author not found for this id");
            throw new NoSuchElementException("Author not found for this id::" + author.getId());
        }
        logger.info("Author has been found");
        Author authorEdited = optionalAuthor.get();
        authorEdited.setName(author.getName());
        authorEdited.setSurname(author.getSurname());
        authorEdited.setGender(author.getGender());
        logger.info("Author has been updated");
        return authorRepository.save(authorEdited);
    }
}
