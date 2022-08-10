module com.example.javafx {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.javafx to javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires lombok;
    exports com.example.javafx;
    exports com.example.javafx.Service;
    opens com.example.javafx.Service to javafx.fxml;
    exports com.example.javafx.Model;
    opens com.example.javafx.Model to javafx.fxml;
    exports com.example.javafx.Controller;
    opens com.example.javafx.Controller to javafx.fxml;
}