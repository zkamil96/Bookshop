package com.example.javafx.Controller;

import com.example.javafx.BookshopApplication;
import com.example.javafx.Model.Author;
import com.example.javafx.Model.Book;
import com.example.javafx.Service.AuthorService;
import com.example.javafx.Service.BookService;
import com.example.javafx.Validator.Validators;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class BookshopController implements Initializable {
    private AuthorService authorService = new AuthorService();
    private BookService bookService = new BookService();
    @FXML
    private TableView<Author> mainTable;
    @FXML
    private TableView<Book> secondTable;
    @FXML
    private Button addAuthor;
    @FXML
    private Button addBook;
    @FXML
    private TextField searchAuthorField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateTableColumns();

        ObservableList<Author> observableAuthorList = authorService.getAuthors();
        mainTable.getItems().addAll(observableAuthorList);

        TextFormatter<String> textFormatter = Validators.letterAndDigitTextFormatter();
        searchAuthorField.setTextFormatter(textFormatter);

        searchAuthorField.textProperty().addListener((observableValue, s, t1) -> {
            List<Author> authors = authorService.searchAuthor(t1.trim());
            mainTable.getItems().clear();
            mainTable.getItems().addAll(authors);
            secondTable.getItems().clear();
        });
    }

    @FXML
    private void clickRowInAuthorTable(MouseEvent event) {
        secondTable.getItems().clear();
        Author author = mainTable.getSelectionModel().getSelectedItem();
        if (author != null) {
            ObservableList<Book> books = bookService.getBooks(author.getId());
            secondTable.getItems().addAll(books);
        }
    }

    @FXML
    private void addAuthorButton(ActionEvent event) {
        Stage stage = makeStage("addAuthor-view.fxml", "Add Author");
        stage.setOnCloseRequest(we -> {
            initAuthorTable();
            secondTable.getItems().clear();
        });
        stage.show();
    }

    @FXML
    private void addBookButton(ActionEvent event) {
        Author selectedItem = mainTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            Stage stage = makeStage("addBook-view.fxml", "Add Book");
            stage.setUserData(selectedItem.getId());
            stage.setOnCloseRequest(we -> initBookTable(selectedItem.getId()));
            stage.show();
        } else {
            makeAlert("Information Dialog", "Select author first");
        }
    }

    @FXML
    private void deleteAuthorButton(ActionEvent event) {
        Author selectedItem = mainTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            authorService.deleteAuthor(selectedItem.getId());
            initAuthorTable();
            secondTable.getItems().clear();
        } else {
            makeAlert("Information Dialog", "Select author first");
        }
    }

    @FXML
    private void deleteBookButton(ActionEvent event) {
        Book book = secondTable.getSelectionModel().getSelectedItem();

        if (book != null) {
            long authorId = book.getAuthorId();
            bookService.deleteBook(book.getId());
            initBookTable(authorId);
        } else {
            makeAlert("Information Dialog", "Select book first");
        }
    }

    public void initAuthorTable() {
        mainTable.getItems().clear();
        mainTable.getItems().addAll(authorService.getAuthors());
    }

    public void initBookTable(long id) {
        secondTable.getItems().clear();
        secondTable.getItems().addAll(bookService.getBooks(id));
    }

    private void makeAlert(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private Stage makeStage(String fxmlName, String stageTitle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BookshopApplication.class.getResource(fxmlName));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle(stageTitle);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.resizableProperty().setValue(false);
            return stage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateTableColumns() {
        List<TableColumn> authorColumns = new ArrayList<>();
        List<TableColumn> bookColumns = new ArrayList<>();
        List<Field> authorFieldNames = List.of(Author.class.getDeclaredFields());
        List<Field> bookFieldNames = List.of(Book.class.getDeclaredFields());
        for (Field field : authorFieldNames) {
            if (!field.getName().equals("books") && !field.getName().equals("id")) {
                authorColumns.add(new TableColumn<Author, String>(field.getName()));
            }
        }
        for (Field field : bookFieldNames) {
            if (!field.getName().equals("books") && !field.getName().equals("id") && !field.getName().equals("authorId")) {
                bookColumns.add(new TableColumn<Book, String>(field.getName()));
            }
        }
        authorColumns.forEach(column -> {
            column.setCellValueFactory(new PropertyValueFactory<Author, String>(column.getText()));
            mainTable.getColumns().add(column);
        });
        bookColumns.forEach(column -> {
            column.setCellValueFactory(new PropertyValueFactory<Book, String>(column.getText()));
            secondTable.getColumns().add(column);
        });
    }


}