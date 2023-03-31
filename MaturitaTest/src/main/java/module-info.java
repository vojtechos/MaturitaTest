module com.example.maturitatest {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.maturitatest to javafx.fxml;
    exports com.example.maturitatest;
}