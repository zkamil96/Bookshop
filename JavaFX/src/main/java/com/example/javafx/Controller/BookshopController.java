package com.example.javafx.Controller;

import com.example.javafx.BookshopApplication;
import com.example.javafx.Model.Author;
import com.example.javafx.Model.Book;
import com.example.javafx.Service.BookshopService;
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
import java.util.*;
import java.util.regex.Pattern;



public class BookshopController implements Initializable {
    private BookshopService bookshopService = new BookshopService();
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
    @FXML
    private void clickRowInAuthorTable(MouseEvent event){
        secondTable.getItems().clear();
        Author author = mainTable.getSelectionModel().getSelectedItem();
        List<Book> books;
        if(author != null){
            books = bookshopService.getBooks(author.getId());
            secondTable.getItems().addAll(books);
        }
    }
    @FXML
    private void addAuthorButton(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(BookshopApplication.class.getResource("addAuthor-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Add Author");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.resizableProperty().setValue(false);
            stage.setOnCloseRequest(we -> {
                initAuthorTable();
                secondTable.getItems().clear();
            });
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void addBookButton(ActionEvent event) {
        Author selectedItem = mainTable.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(BookshopApplication.class.getResource("addBook-view.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setTitle("Add Book");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setUserData(selectedItem.getId());
                stage.resizableProperty().setValue(false);
                stage.setOnCloseRequest(we -> initBookTable(selectedItem.getId()));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Select author first");
            alert.showAndWait();
        }
    }
    @FXML
    private void deleteAuthorButton(ActionEvent event){
        Author selectedItem = mainTable.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            bookshopService.deleteAuthor(selectedItem.getId());
            initAuthorTable();
            secondTable.getItems().clear();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Select author first");
            alert.showAndWait();
        }
    }
    @FXML
    private void deleteBookButton(ActionEvent event){
        Book book = secondTable.getSelectionModel().getSelectedItem();
        if(book != null){
            long authorId = book.getAuthorId();
            bookshopService.deleteBook(book.getId());
            initBookTable(authorId);
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Select book first");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateTableColumns();
        ObservableList<Author> observableAuthorList = bookshopService.getAuthors();
        mainTable.getItems().addAll(observableAuthorList);

        TextFormatter<String> textFormatter = letterAndDigitTextFormatter();
        searchAuthorField.setTextFormatter(textFormatter);
        searchAuthorField.textProperty().addListener((observableValue, s, t1) -> {
                List<Author> authors = bookshopService.searchAuthor(t1.trim());
                mainTable.getItems().clear();
                mainTable.getItems().addAll(authors);
                secondTable.getItems().clear();
        });
    }

    public void initAuthorTable(){
        mainTable.getItems().clear();
        mainTable.getItems().addAll(bookshopService.getAuthors());
    }

    public void initBookTable(long id){
        secondTable.getItems().clear();
        secondTable.getItems().addAll(bookshopService.getBooks(id));
    }

    private void generateTableColumns() {
        List<TableColumn> authorColumns = new ArrayList<>();
        List<TableColumn> bookColumns = new ArrayList<>();
        List<Field> authorFieldNames = List.of(new Author().getClass().getDeclaredFields());
        List<Field> bookFieldNames = List.of(new Book().getClass().getDeclaredFields());
        for (Field field:authorFieldNames) {
            if(!field.getName().equals("books") && !field.getName().equals("id")){
                authorColumns.add(new TableColumn<Author, String>(field.getName()));
            }
        }
        for (Field field:bookFieldNames) {
            if(!field.getName().equals("books") && !field.getName().equals("id") && !field.getName().equals("authorId")){
                bookColumns.add(new TableColumn<Book, String>(field.getName()));
            }
        }
        authorColumns.forEach(column->{
            column.setCellValueFactory(new PropertyValueFactory<Author, String>(column.getText()));
            mainTable.getColumns().add(column);
        });
        bookColumns.forEach(column->{
            column.setCellValueFactory(new PropertyValueFactory<Book, String>(column.getText()));
            secondTable.getColumns().add(column);
        });
    }

    public static TextFormatter<String> letterAndDigitTextFormatter() {
        final Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            if(pattern.matcher(change.getControlNewText()).matches()){
                return change;
            }else{
                return null;
            }
        });
        return textFormatter;
    }


}