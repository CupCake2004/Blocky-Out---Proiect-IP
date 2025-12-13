package com.example.proiectip.view;

import com.example.proiectip.controller.GameManager;
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
        // Design Modern - Gradient
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom right, #2c3e50, #4ca1af);");

        Label label = new Label("SELECTEAZĂ NIVELUL");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 35));
        label.setTextFill(Color.WHITE);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(30);
        grid.setVgap(30);

        // --- INTEROGARE SINGLETON ---
        // Întrebăm Managerul care este maximul deblocat
        int maxUnlocked = GameManager.getInstance().getMaxUnlockedLevel();

        // Facem doar 5 nivele
        for (int i = 0; i < 5; i++) {
            int levelNum = i + 1;

            Button btnLevel = new Button(String.valueOf(levelNum));
            btnLevel.setPrefSize(80, 80);

            // Verificăm dacă nivelul este permis
            if (levelNum <= maxUnlocked) {
                // NIVEL DEBLOCAT (Albastru - Activ)
                btnLevel.setStyle(
                        "-fx-background-color: #3498db; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 24px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-background-radius: 15; " +
                                "-fx-cursor: hand;"
                );

                // Adăugăm efect la mouse over
                btnLevel.setOnMouseEntered(e -> btnLevel.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold; -fx-background-radius: 15;"));
                btnLevel.setOnMouseExited(e -> btnLevel.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold; -fx-background-radius: 15;"));

                btnLevel.setOnAction(e -> {
                    new GameScreen(stage, levelNum).show();
                });
            } else {
                // NIVEL BLOCAT (Gri - Dezactivat)
                btnLevel.setStyle(
                        "-fx-background-color: #7f8c8d; " +
                                "-fx-text-fill: #bdc3c7; " +
                                "-fx-font-size: 24px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-background-radius: 15;"
                );
                btnLevel.setDisable(true); // Nu poți da click
            }

            grid.add(btnLevel, i, 0);
        }

        Button btnBack = new Button("Înapoi");
        btnBack.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18px; -fx-border-color: white; -fx-border-radius: 20; -fx-padding: 5 20;");
        btnBack.setOnAction(e -> new StartMenu(stage).show());

        mainLayout.getChildren().addAll(label, grid, btnBack);
        Scene scene = new Scene(mainLayout, 900, 750);
        stage.setScene(scene);
    }
}