package com.example.proiectip.view;

import com.example.proiectip.controller.GameManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LevelSelect {
    private Stage stage;

    public LevelSelect(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        VBox mainLayout = new VBox(30);
        mainLayout.setAlignment(Pos.CENTER);

        Label label = new Label("SELECT LEVEL");
        label.setStyle("-fx-font-size: 30px;");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);

        // Generăm butoane de la 1 la 10
        int count = 1;
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 5; col++) {
                int levelNum = count;
                Button btnLevel = new Button("Level " + levelNum);
                btnLevel.setPrefSize(100, 50);

                btnLevel.setOnAction(e -> {
                    // Salvăm nivelul în Singleton
                    GameManager.getInstance().setCurrentLevel(levelNum);
                    // Aici vom porni jocul propriu-zis (GameScreen)
                    GameScreen game = new GameScreen(stage, levelNum);
                    game.show();
                    System.out.println("Starting Level " + levelNum);
                    // TODO: GameScreen.show(stage);
                });

                grid.add(btnLevel, col, row);
                count++;
            }
        }

        mainLayout.getChildren().addAll(label, grid);
        Scene scene = new Scene(mainLayout, 800, 600);
        stage.setScene(scene);
    }
}