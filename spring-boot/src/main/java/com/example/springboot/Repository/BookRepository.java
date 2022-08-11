package com.example.springboot.Repository;

import com.example.springboot.Repository.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
