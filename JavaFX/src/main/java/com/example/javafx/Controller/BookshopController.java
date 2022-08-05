package com.example.javafx.Controller;

import com.example.javafx.Model.Author;
import com.example.javafx.Model.Book;
import com.example.javafx.Service.BookshopService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;


public class BookshopController implements Initializable {
    @FXML
    private TableView<Author> mainTable;
    @FXML
    private TableView<Book> secondTable;
    @FXML
    private Button addOrDelete;
    @FXML
    private TextField search;
    @FXML
    public void clickRowInAuthorTable(MouseEvent event){
        secondTable.getItems().clear();
        Author author = mainTable.getSelectionModel().getSelectedItem();
        Author singleAuthor;
        List<Book> books = new ArrayList<>();
        if(author != null){
            BookshopService authorService = new BookshopService();
            singleAuthor = authorService.getSingleAuthor(author.getId());
            books.addAll(singleAuthor.getBooks());
            secondTable.getItems().addAll(books);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BookshopService authorService = new BookshopService();
        ObservableList<Author> observableAuthorList = authorService.getAuthors();
        List<Book> booksList = new ArrayList<>();
        observableAuthorList.forEach(author -> {
            booksList.addAll(author.getBooks());
        });
        List<TableColumn> authorColumns = new ArrayList<>();
        List<TableColumn> bookColumns = new ArrayList<>();
        List<Field> authorFieldNames = List.of(new Author().getClass().getDeclaredFields());
        List<Field> bookFieldNames = List.of(new Book().getClass().getDeclaredFields());
        for (Field field:authorFieldNames) {
            if(!field.getName().equals("books")){
                authorColumns.add(new TableColumn<Author, String>(field.getName()));
            }
        }
        for (Field field:bookFieldNames) {
                bookColumns.add(new TableColumn<Book, String>(field.getName()));
        }
        authorColumns.forEach(column->{
            column.setCellValueFactory(new PropertyValueFactory<Author, String>(column.getText()));
            mainTable.getColumns().add(column);
        });
        mainTable.getItems().addAll(observableAuthorList);

        bookColumns.forEach(column->{
            column.setCellValueFactory(new PropertyValueFactory<Book, String>(column.getText()));
            secondTable.getColumns().add(column);
        });
    }
}