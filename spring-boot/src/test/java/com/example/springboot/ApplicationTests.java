package com.example.springboot;

import com.example.springboot.Model.Author;
import com.example.springboot.Model.Book;
import com.example.springboot.Repository.AuthorRepository;
import com.example.springboot.Repository.BookRepository;
import com.example.springboot.Service.BookshopService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
class ApplicationTests {

	@Autowired
	private BookshopService bookshopService;
	@MockBean
	private AuthorRepository authorRepository;
	@MockBean
	private BookRepository bookRepository;

	@Test
	public void addAuthorTest(){
		Book book = new Book(1,1,"title", "publisher", "category", "year");
		List<Book> books = new ArrayList<>();
		books.add(book);
		Author author = new Author(1, "kamil", "zarebski", "male", books);
		when(authorRepository.save(author)).thenReturn(author);
		assertEquals(author, bookshopService.addAuthor(author));
	}

	@Test
	public void addBookTest(){
		Book book = new Book(1,1,"title", "publisher", "category", "year");
		when(bookRepository.save(book)).thenReturn(book);
		assertEquals(book, bookshopService.addBook(book, book.getAuthorId()));
	}
	@Test
	public void deleteAuthorTest(){
		bookshopService.deleteAuthor(1L);
		verify(authorRepository, times(1)).deleteById(1L);
	}

	@Test
	public void deleteBookTest(){
		bookshopService.deleteBook(1L);
		verify(bookRepository, times(1)).deleteById(1L);
	}


}
