package com.example.javafx.Controller;

import com.example.javafx.Model.Author;
import com.example.javafx.Service.AuthorService;
import com.example.javafx.Validator.Validators;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

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
    private void addAuthor(ActionEvent event) {
        Author author = new Author();
        String name = nameField.getText().trim();
        String surname = surnameField.getText().trim();
        String gender = genderField.getValue().toString();
        if (name.isEmpty() || surname.isEmpty()) {
            alertAuthor.setText("All fields must be filled");
        } else {
            alertAuthor.setText("");
            author.setName(name);
            author.setSurname(surname);
            author.setGender(gender);
            AuthorService authorService = new AuthorService();
            authorService.addAuthor(author);
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genderField.getItems().addAll("male", "female");
        genderField.setValue("male");
        nameField.setTextFormatter(Validators.letterTextFormatter());
        surnameField.setTextFormatter(Validators.letterTextFormatter());
    }


}
