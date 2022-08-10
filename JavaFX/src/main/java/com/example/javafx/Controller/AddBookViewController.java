package com.example.javafx.Controller;

import com.example.javafx.Model.Book;
import com.example.javafx.Service.BookshopService;
import com.example.javafx.Validator.Validators;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBookViewController implements Initializable {
    @FXML
    private TextField titleField;
    @FXML
    private TextField publisherField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField releaseYearField;
    @FXML
    private Button addBook;
    @FXML
    private Label alertBook;

    @FXML
    private void addBook(ActionEvent event) {
        Book book = new Book();
        String title = titleField.getText().trim();
        String publisher = publisherField.getText().trim();
        String category = categoryField.getText();
        String releaseYear = releaseYearField.getText().trim();
        if (title.isEmpty() || publisher.isEmpty() || category.isEmpty() || releaseYear.isEmpty()) {
            alertBook.setText("All fields must be filled");
        } else if (releaseYear.length() < 4) {
            alertBook.setText("Year must have 4 digit");
        } else {
            alertBook.setText("");
            book.setTitle(title);
            book.setPublisher(publisher);
            book.setCategory(category);
            book.setReleaseYear(releaseYear);
            BookshopService bookshopService = new BookshopService();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            long id = (long) stage.getUserData();
            bookshopService.addBook(book, id);
            stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleField.setTextFormatter(Validators.letterTextFormatter());
        publisherField.setTextFormatter(Validators.letterTextFormatter());
        categoryField.setTextFormatter(Validators.letterTextFormatter());
        releaseYearField.setTextFormatter(Validators.digitTextFormatter());

    }

}
