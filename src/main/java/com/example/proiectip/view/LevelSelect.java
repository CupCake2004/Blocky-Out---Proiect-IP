package com.example.proiectip.view;

import com.example.proiectip.controller.GameManager; // Asigură-te că ai asta sau șterge dacă nu folosești GameManager încă
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LevelSelect {
    private Stage stage;

    public LevelSelect(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        VBox mainLayout = new VBox(40);
        mainLayout.setAlignment(Pos.CENTER);
        // Același gradient ca la meniu pentru consistență
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom right, #2c3e50, #4ca1af);");

        Label label = new Label("SELECTEAZĂ NIVELUL");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 35));
        label.setTextFill(Color.WHITE);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(30);
        grid.setVgap(30);

        // --- DOAR 5 NIVELE ---
        int count = 1;
        for (int i = 0; i < 5; i++) { // Facem doar un rând de 5
            int levelNum = count;

            Button btnLevel = new Button(String.valueOf(levelNum));
            btnLevel.setPrefSize(80, 80);

            // Stil butoane pătrate moderne
            String normalStyle =
                    "-fx-background-color: #3498db; " + // Albastru
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 24px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-background-radius: 15; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 10, 0, 0, 5);";

            btnLevel.setStyle(normalStyle);

            btnLevel.setOnAction(e -> {
                // Pornim jocul cu nivelul selectat
                GameScreen game = new GameScreen(stage, levelNum);
                game.show();
            });

            grid.add(btnLevel, i, 0); // Adăugăm pe coloana i, rândul 0
            count++;
        }

        Button btnBack = new Button("Înapoi");
        btnBack.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18px; -fx-border-color: white; -fx-border-radius: 20; -fx-padding: 5 20;");
        btnBack.setOnAction(e -> new StartMenu(stage).show());

        mainLayout.getChildren().addAll(label, grid, btnBack);
        Scene scene = new Scene(mainLayout, 900, 750);
        stage.setScene(scene);
    }
}