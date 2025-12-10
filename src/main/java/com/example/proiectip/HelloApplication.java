package com.example.proiectip;

import com.example.proiectip.view.StartMenu; // <--- Importăm meniul creat de noi
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // În loc să încărcăm acel fxml vechi, pornim direct meniul nostru
        StartMenu menu = new StartMenu(stage);
        menu.show();
    }

    public static void main(String[] args) {
        launch();
    }
}