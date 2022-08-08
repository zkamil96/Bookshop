package com.example.javafx.Controller;

import com.example.javafx.Model.Author;
import com.example.javafx.Service.BookshopService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddAuthorViewController implements Initializable {
    @FXML
    private TableView<Author> mainTable;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private ChoiceBox genderField;
    @FXML
    private Button add;
    @FXML
    private Label alertAuthor;
    @FXML
    private void addAuthor(ActionEvent event){
        Author author = new Author();
        String name = nameField.getText().trim();
        String surname = surnameField.getText().trim();
        String gender = genderField.getValue().toString();
        if(name.isEmpty() || surname.isEmpty()){
            alertAuthor.setText("All fields must be filled");
        }else{
            alertAuthor.setText("");
            author.setName(name);
            author.setSurname(surname);
            author.setGender(gender);
            BookshopService bookshopService = new BookshopService();
            bookshopService.addAuthor(author);
            Node source = (Node) event.getSource();
            Stage stage  = (Stage) source.getScene().getWindow();
            stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genderField.getItems().addAll("male", "female");
        genderField.setValue("male");
        nameField.setTextFormatter(letterTextFormatter());
        surnameField.setTextFormatter(letterTextFormatter());
    }

    public static TextFormatter<String> letterTextFormatter() {
        final Pattern pattern = Pattern.compile("[a-zA-Z]*");
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
