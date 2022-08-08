package com.example.springboot.Repository;

import com.example.springboot.Model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findAuthorByNameStartingWithOrSurnameStartingWithOrGenderStartingWith(String name, String surname, String gender);

}
