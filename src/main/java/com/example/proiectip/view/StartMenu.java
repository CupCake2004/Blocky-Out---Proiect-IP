package com.example.proiectip.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class StartMenu {

    private Stage stage;

    public StartMenu(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        VBox layout = new VBox(30);
        layout.setAlignment(Pos.CENTER);

        // Fundal Gradient Modern (Albastru închis spre Turcoaz)
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #2c3e50, #4ca1af);");

        // Titlu cu efect de umbră
        Label title = new Label("BLOCKY OUT");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 60));
        title.setTextFill(Color.WHITE);

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.BLACK);
        shadow.setRadius(10);
        shadow.setOffsetX(5);
        shadow.setOffsetY(5);
        title.setEffect(shadow);

        // Buton Stilizat
        Button btnStart = new Button("JOACĂ");
        btnStart.setStyle(
                "-fx-background-color: #e74c3c; " + // Roșu aprins
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 24px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 30; " + // Rotunjit
                        "-fx-padding: 10 40 10 40;" +
                        "-fx-cursor: hand;"
        );

        // Efect la hover (când pui mouse-ul peste buton)
        btnStart.setOnMouseEntered(e -> btnStart.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold; -fx-background-radius: 30; -fx-padding: 10 40 10 40;"));
        btnStart.setOnMouseExited(e -> btnStart.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold; -fx-background-radius: 30; -fx-padding: 10 40 10 40;"));

        btnStart.setOnAction(e -> {
            LevelSelect levelSelect = new LevelSelect(stage);
            levelSelect.show();
        });

        layout.getChildren().addAll(title, btnStart);
        Scene scene = new Scene(layout, 900, 750); // Aceeași mărime ca jocul
        stage.setScene(scene);
        stage.setTitle("Blocky Out - Meniu");
        stage.show();
    }
}