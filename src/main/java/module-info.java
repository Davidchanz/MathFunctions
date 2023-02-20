module com.example.mathfunctions {
    requires javafx.controls;
    requires javafx.fxml;
    requires FXChemiEngine;


    opens com.example.mathfunctions to javafx.fxml;
    exports com.example.mathfunctions;
}