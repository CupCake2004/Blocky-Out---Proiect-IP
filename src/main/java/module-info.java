module com.example.proiectip {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.proiectip to javafx.fxml;
    exports com.example.proiectip;
}