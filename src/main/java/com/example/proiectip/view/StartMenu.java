package com.example.proiectip.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StartMenu {

    private Stage stage;

    public StartMenu(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        VBox layout = new VBox(20); // 20px spațiu între elemente
        layout.setAlignment(Pos.CENTER);

        Label title = new Label("BLOCKY OUT");
        title.setFont(new Font("Arial", 40));

        Button btnStart = new Button("START GAME");
        btnStart.setStyle("-fx-font-size: 20px; -fx-padding: 10px 30px;");

        // Când apăsăm Start, mergem la Selectarea Nivelelor
        btnStart.setOnAction(e -> {
            LevelSelect levelSelect = new LevelSelect(stage);
            levelSelect.show();
        });

        layout.getChildren().addAll(title, btnStart);
        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Blocky Out - Menu");
        stage.show();
    }
}